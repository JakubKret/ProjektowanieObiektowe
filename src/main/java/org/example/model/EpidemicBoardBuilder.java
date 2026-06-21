package org.example.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class EpidemicBoardBuilder implements BoardBuilder {
    private Board board;
    private final Random rand = new Random();

    public EpidemicBoardBuilder() {
        this.board = new Board();
    }

    @Override
    public void buildTerrain() {
        SimulationConfig config = SimulationConfig.getInstance();
        int[][] gradientValues = generateGradientValues(board.width, board.height);

        Tile[][] table = new Tile[board.width][board.height];
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                double nx = x / (double) board.width;
                double ny = y / (double) board.height;
                double noiseValue = board.noise(nx * 10, ny * 10);
                int noiseGray = (int) ((noiseValue + 1) * 127.5);
                int baseGray = fastFloor((noiseGray + fastFloor(1.75 * gradientValues[x][y])) / 2.75);

                table[x][y] = new Tile(baseGray, x, y);
            }
        }
        board.setBoardTable(table);
    }

    @Override
    public void buildPopulation() {
        SimulationConfig config = SimulationConfig.getInstance();
        Tile[][] table = board.getBoardTable();

        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                if (table[x][y].isLand()) {
                    if (rand.nextDouble() < config.density) {
                        int number = rand.nextInt(config.maxPeoplePerTile + 1);
                        for (int z = 0; z < number; z++) {
                            Human human = EntityFactory.createHuman(x, y);
                            table[x][y].addEntity(human);
                            board.addEntityToPopulation(human);
                        }
                    }
                    if (rand.nextDouble() < 0.05) {
                        int number = rand.nextInt(config.maxAnimalsPerTile + 1);
                        for (int z = 0; z < number; z++) {
                            Animal animal = rand.nextBoolean() ? EntityFactory.createRat(x, y) : EntityFactory.createBat(x, y);
                            table[x][y].addEntity(animal);
                            board.addEntityToPopulation(animal);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void buildIslandsAndAirports() {
        ArrayList<ArrayList<Tile>> lands = findIslands();
        for (ArrayList<Tile> islandTiles : lands) {
            Island island = new Island(islandTiles);
            int r = rand.nextInt(island.getIslandLand().size());
            island.setAirport(island.getIslandLand().get(r));
            island.getIslandLand().get(r).setAirport();
            board.addIsland(island);
        }
    }

    @Override
    public void buildPlanes() {
        for (Island island : board.getIslands()) {
            Plane plane = new Plane(island.getAirport().getPosX(), island.getAirport().getPosY(), (ArrayList<Island>) board.getIslands(), board.getBoardTable());
            board.addPlane(plane);
        }
    }

    @Override
    public Board getResult() {
        return this.board;
    }

    private int[][] generateGradientValues(int width, int height) {
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

    private ArrayList<ArrayList<Tile>> findIslands() {
        ArrayList<ArrayList<Tile>> lands = new ArrayList<>();
        boolean[][] visited = new boolean[board.width][board.height];
        Tile[][] table = board.getBoardTable();
        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                if (table[i][j].isLand() && !visited[i][j]) {
                    ArrayList<Tile> islandTiles = new ArrayList<>();
                    dfs(visited, i, j, islandTiles, table);
                    lands.add(islandTiles);
                }
            }
        }
        return lands;
    }

    private void dfs(boolean[][] visited, int row, int col, ArrayList<Tile> islandTiles, Tile[][] table) {
        int[] rowDir = {-1, 1, 0, 0};
        int[] colDir = {0, 0, -1, 1};
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int r = current[0];
            int c = current[1];
            if (r < 0 || r >= board.width || c < 0 || c >= board.height || visited[r][c] || !table[r][c].isLand()) continue;
            visited[r][c] = true;
            islandTiles.add(table[r][c]);
            for (int k = 0; k < 4; k++) stack.push(new int[]{r + rowDir[k], c + colDir[k]});
        }
    }

    private static int fastFloor(double x) { return x > 0 ? (int) x : (int) x - 1; }
}