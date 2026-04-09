package org.example.model;

import org.example.state.DeadState;
import org.example.state.InfectedState;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Board {
    public final int width = 400;
    public final int height = 400;

    public final int speed;
    public final double contagiousness;
    public final double deathRate;
    public final double healRate;
    public final int delay;
    public final double density;
    public final double scale;
    public final int maxPeoplePerTile;
    public final int maxAnimalsPerTile;
    public int iter;

    private Tile[][] boardTable = new Tile[width][height];
    private List<Entity> population = new ArrayList<>();
    private List<Plane> planes = new ArrayList<>();
    private List<Island> islands = new ArrayList<>();

    private int tickCount = 0;
    private final Random rand = new Random();

    public Board(int speed, double contagiousness, double deathRate, double healRate,
                 int delay, double density, double scale,
                 int maxPeople, int maxAnimals, int iter) {
        this.speed = speed;
        this.contagiousness = contagiousness / 100.0;
        this.deathRate = deathRate / 100.0;
        this.healRate = healRate / 100.0;
        this.density = density / 100.0;
        this.delay = delay;
        this.scale = scale / 10.0;
        this.maxPeoplePerTile = maxPeople;
        this.maxAnimalsPerTile = maxAnimals;
        this.iter = iter;

        generateMap();
    }

    private void generateMap() {
        int[][] gradientValues = generateGradientValues();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double nx = x / (double) width;
                double ny = y / (double) height;
                double noiseValue = noise(nx * 10, ny * 10);
                int noiseGray = (int) ((noiseValue + 1) * 127.5);
                int baseGray = fastFloor((noiseGray + fastFloor(1.75 * gradientValues[x][y])) / 2.75);

                boardTable[x][y] = new Tile(baseGray, x, y);

                if (boardTable[x][y].isLand()) {
                    if (rand.nextDouble() < density) {
                        int number = rand.nextInt(maxPeoplePerTile + 1);
                        for (int z = 0; z < number; z++) {
                            Human human = new Human(x, y);
                            boardTable[x][y].addEntity(human);
                            population.add(human);
                        }
                    }
                    if (rand.nextDouble() < 0.05) {
                        int number = rand.nextInt(maxAnimalsPerTile + 1);
                        for (int z = 0; z < number; z++) {
                            Animal animal = rand.nextBoolean() ? new Rat(x, y) : new Bat(x, y);
                            boardTable[x][y].addEntity(animal);
                            population.add(animal);
                        }
                    }
                }
            }
        }
        groupIslands();
        generateAirports();
        createPlanes();
    }

    public void patientZero() {
        if (!population.isEmpty()) {
            Entity zero = population.get(rand.nextInt(population.size()));
            zero.changeState(new InfectedState());
        }
    }

    public void tick() {
        tickCount++;

        for (Entity entity : population) {
            boardTable[entity.getPosX()][entity.getPosY()].removeEntity(entity);
            entity.move(boardTable, width, height);
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
                    for (Entity e : tile.getEntities()) e.getExposed(contagiousness);
                }
            }
        }

        for (Entity e : population) e.passTurn(deathRate);

        if (tickCount >= delay) {
            if (tickCount % 2 == 0) {
                for (int k = 0; k < iter; k++) {
                    int x = rand.nextInt(width);
                    int y = rand.nextInt(height);
                    for (Entity e : boardTable[x][y].getEntities()) e.receiveTreatment(healRate, contagiousness);
                }
                if (iter < (height * height) / 150) iter++;
            }
        }
    }

    public void showStats() {
        int healthy = 0, dead = 0, infected = 0;
        int humanCount = 0;
        for (Entity e : population) {
            if(e instanceof Human) {
                humanCount++;
                if (e.getHealthState() instanceof DeadState) dead++;
                else if (e.getHealthState() instanceof InfectedState) infected++;
                else healthy++;
            }
        }
        if(humanCount == 0) return;
        System.out.println("________________________________________");
        System.out.println("Healthy " + (int) (healthy * 100.0 / humanCount) + "%");
        System.out.println("Infected " + (int) (infected * 100.0 / humanCount) + "%");
        System.out.println("Dead " + (int) (dead * 100.0 / humanCount) + "%");
    }

    private void groupIslands() {
        ArrayList<ArrayList<Tile>> lands = findIslands();
        for (ArrayList<Tile> islandTiles : lands) islands.add(new Island(islandTiles));
    }

    private ArrayList<ArrayList<Tile>> findIslands() {
        ArrayList<ArrayList<Tile>> lands = new ArrayList<>();
        boolean[][] visited = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardTable[i][j].isLand() && !visited[i][j]) {
                    ArrayList<Tile> islandTiles = new ArrayList<>();
                    dfs(visited, i, j, islandTiles);
                    lands.add(islandTiles);
                }
            }
        }
        return lands;
    }

    private void dfs(boolean[][] visited, int row, int col, ArrayList<Tile> islandTiles) {
        int[] rowDir = {-1, 1, 0, 0};
        int[] colDir = {0, 0, -1, 1};
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int r = current[0];
            int c = current[1];

            if (r < 0 || r >= width || c < 0 || c >= height || visited[r][c] || !boardTable[r][c].isLand()) continue;

            visited[r][c] = true;
            islandTiles.add(boardTable[r][c]);

            for (int k = 0; k < 4; k++) stack.push(new int[]{r + rowDir[k], c + colDir[k]});
        }
    }

    private void generateAirports() {
        for (Island island : islands) {
            int r = rand.nextInt(island.getIslandLand().size());
            island.setAirport(island.getIslandLand().get(r));
            island.getIslandLand().get(r).setAirport();
        }
    }

    private void createPlanes() {
        for (Island island : islands) planes.add(new Plane(island.getAirport().getPosX(), island.getAirport().getPosY(), (ArrayList<Island>)islands, boardTable));
    }

    private int[][] generateGradientValues() {
        int[][] values = new int[width][height];
        Point2D center = new Point2D.Float(fastFloor(width / 2.0), fastFloor(height / 2.0));
        float radius = fastFloor(Math.max(width, height) / 1.25);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double dx = x - center.getX();
                double dy = y - center.getY();
                values[x][y] = (int) (255 * Math.min(Math.sqrt(dx * dx + dy * dy) / radius, 1.0));
            }
        }
        return values;
    }

    private static double noise(double xin, double yin) {
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
    private static int fastFloor(double x) { return x > 0 ? (int) x : (int) x - 1; }

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
}