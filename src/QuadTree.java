



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 *  			N
 *  		W		E
 *  			S
 */


/* Using two points of Rectangular (Top,Left) & (Bottom , Right)*/
class Boundry {
	public int getxMin() {
		return xMin;
	}

	public int getyMin() {
		return yMin;
	}

	public int getxMax() {
		return xMax;
	}

	public int getyMax() {
		return yMax;
	}

	public Boundry(int xMin, int yMin, int xMax, int yMax) {
		super();
		/*
		 *  Storing two diagonal points 
		 */
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}

	public boolean inRange(int x, int y) {
		return (x >= this.getxMin() && x <= this.getxMax()
				&& y >= this.getyMin() && y <= this.getyMax());
	}

	int xMin, yMin, xMax, yMax;

}

public class QuadTree {
	
	final int MAX_CAPACITY =4;
	int level = 0;
	List<Abeja> Bees;
	QuadTree northWest = null;
	QuadTree northEast = null;
	QuadTree southWest = null;
	QuadTree southEast = null;
	Boundry boundry;

	QuadTree(int level, Boundry boundry) {
		this.level = level;
		Bees = new ArrayList<Abeja>();
		this.boundry = boundry;
	}

	/* Traveling the Graph using Depth First Search*/
	static void dfs(QuadTree tree) {
		if (tree == null)
			return;

		System.out.printf("\nLevel = %d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
				tree.level, tree.boundry.getxMin(), tree.boundry.getyMin(),
				tree.boundry.getxMax(), tree.boundry.getyMax());

		for (Abeja bee : tree.Bees) {
			System.out.printf(" \n\t  x=%d y=%d", bee.x, bee.y);
		}
		if (tree.Bees.size() == 0) {
			System.out.printf(" \n\t  Leaf Node.");
		}
		dfs(tree.northWest);
		dfs(tree.northEast);
		dfs(tree.southWest);
		dfs(tree.southEast);

	}

	void split() {
		int xOffset = this.boundry.getxMin()
				+ (this.boundry.getxMax() - this.boundry.getxMin()) / 2;
		int yOffset = this.boundry.getyMin()
				+ (this.boundry.getyMax() - this.boundry.getyMin()) / 2;

		northWest = new QuadTree(this.level + 1, new Boundry(
				this.boundry.getxMin(), this.boundry.getyMin(), xOffset,
				yOffset));
		northEast = new QuadTree(this.level + 1, new Boundry(xOffset,
				this.boundry.getyMin(), xOffset, yOffset));
		southWest = new QuadTree(this.level + 1, new Boundry(
				this.boundry.getxMin(), xOffset, xOffset,
				this.boundry.getyMax()));
		southEast = new QuadTree(this.level + 1, new Boundry(xOffset, yOffset,
				this.boundry.getxMax(), this.boundry.getyMax()));

	}

	void insert(int x, int y) {
		if (!this.boundry.inRange(x, y)) {
			return;
		}

		Abeja bee = new Abeja(x, y);
		if (Bees.size() < MAX_CAPACITY) {
			Bees.add(bee);
			return;
		}
		// Exceeded the capacity so split it in FOUR
		if (northWest == null) {
			split();
		}

		// Check coordinates belongs to which partition 
		if (this.northWest.boundry.inRange(x, y))
			this.northWest.insert(x, y);
		else if (this.northEast.boundry.inRange(x, y))
			this.northEast.insert(x, y);
		else if (this.southWest.boundry.inRange(x, y))
			this.southWest.insert(x, y);
		else if (this.southEast.boundry.inRange(x, y))
			this.southEast.insert(x, y);
		else
			System.out.printf("ERROR : Unhandled partition %d %d", x, y);
	}

	
        
		
	}



 