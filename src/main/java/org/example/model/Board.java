package org.example.model;

import org.example.observer.SimulationObserver;
import org.example.state.InfectedState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public final int width = 400;
    public final int height = 400;

    private Tile[][] boardTable = new Tile[width][height];
    private List<Entity> population = new ArrayList<>();
    private List<Plane> planes = new ArrayList<>();
    private List<Island> islands = new ArrayList<>();
    private List<SimulationObserver> observers = new ArrayList<>();

    private int tickCount = 0;
    private final Random rand = new Random();

    public Board() {
    }

    public void addObserver(SimulationObserver observer) {
        observers.add(observer);
    }

    public void patientZero() {
        if (!population.isEmpty()) {
            Entity zero = population.get(rand.nextInt(population.size()));
            zero.changeState(new InfectedState());
        }
    }

    public void tick() {
        tickCount++;
        SimulationConfig config = SimulationConfig.getInstance();

        for (Entity entity : population) {
            boardTable[entity.getPosX()][entity.getPosY()].removeEntity(entity);
            entity.performTurn(boardTable, width, height);
            boardTable[entity.getPosX()][entity.getPosY()].addEntity(entity);
        }

        for (Plane plane : planes) {
            boardTable[plane.getPosX()][plane.getPosY()].getPlanes().remove(plane);
            plane.Move();
            boardTable[plane.getPosX()][plane.getPosY()].getPlanes().add(plane);
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = boardTable[x][y];
                boolean hasInfected = false;
                for (Entity e : tile.getEntities()) {
                    if (e.getHealthState().canInfectOthers()) { hasInfected = true; break; }
                }
                if (hasInfected) {
                    for (Entity e : tile.getEntities()) e.getExposed(config.contagiousness);
                }
            }
        }

        if (tickCount >= config.delay) {
            if (tickCount % 2 == 0) {
                for (int k = 0; k < config.iter; k++) {
                    int x = rand.nextInt(width);
                    int y = rand.nextInt(height);
                    for (Entity e : boardTable[x][y].getEntities()) {
                        e.receiveTreatment(config.healRate, config.contagiousness);
                    }
                }
                if (config.iter < (height * height) / 150) config.iter++;
            }
        }

        notifyObservers();
    }

    private void notifyObservers() {
        for (SimulationObserver observer : observers) {
            observer.onSimulationTick(this);
        }
    }

    public static double noise(double xin, double yin) {
        double s = (xin + yin) * 0.5 * (Math.sqrt(3.0) - 1.0);
        int i = fastFloor(xin + s);
        int j = fastFloor(yin + s);
        double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
        double t = (i + j) * G2;
        double X0 = i - t;
        double Y0 = j - t;
        double x0 = xin - X0;
        double y0 = yin - Y0;

        int i1 = (x0 > y0) ? 1 : 0;
        int j1 = (x0 > y0) ? 0 : 1;

        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = y0 - 1.0 + 2.0 * G2;

        int ii = i & 255;
        int jj = j & 255;
        int gi0 = PERM_MOD12[ii + PERM[jj]];
        int gi1 = PERM_MOD12[ii + i1 + PERM[jj + j1]];
        int gi2 = PERM_MOD12[ii + 1 + PERM[jj + 1]];

        double t0 = 0.5 - x0 * x0 - y0 * y0;
        double n0 = (t0 < 0) ? 0.0 : t0 * t0 * t0 * t0 * dot(GRAD3[gi0], x0, y0);

        double t1 = 0.5 - x1 * x1 - y1 * y1;
        double n1 = (t1 < 0) ? 0.0 : t1 * t1 * t1 * t1 * dot(GRAD3[gi1], x1, y1);

        double t2 = 0.5 - x2 * x2 - y2 * y2;
        double n2 = (t2 < 0) ? 0.0 : t2 * t2 * t2 * t2 * dot(GRAD3[gi2], x2, y2);

        return 70.0 * (n0 + n1 + n2);
    }

    private static double dot(int[] g, double x, double y) { return g[0] * x + g[1] * y; }
    public static int fastFloor(double x) { return x > 0 ? (int) x : (int) x - 1; }

    private static final int[][] GRAD3 = {
            {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0},
            {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1},
            {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
    };
    private static final int[] p = new int[256];
    private static final int[] PERM = new int[512];
    private static final int[] PERM_MOD12 = new int[512];

    static {
        for (int i = 0; i < 256; i++) p[i] = i;
        for (int i = 0; i < 256; i++) {
            int j = (int) (Math.random() * 256);
            int temp = p[i];
            p[i] = p[j];
            p[j] = temp;
        }
        for (int i = 0; i < 512; i++) {
            PERM[i] = p[i & 255];
            PERM_MOD12[i] = PERM[i] % 12;
        }
    }

    public Tile[][] getBoardTable() { return boardTable; }
    public List<Entity> getPopulation() { return population; }
    public List<Island> getIslands() { return islands; }

    public void setBoardTable(Tile[][] table) { this.boardTable = table; }
    public void addEntityToPopulation(Entity e) { this.population.add(e); }
    public void addIsland(Island island) { this.islands.add(island); }
    public void addPlane(Plane plane) { this.planes.add(plane); }
}