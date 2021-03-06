import java.util.ArrayList;
import java.util.HashMap;

class State {

	ArrayList<State> childrenStates = new ArrayList<>();
	public State parent;
	public int[][] currentState;
	public int x = 0, y = 0;
	public Move move;
	public int movePriority;
	public int heuristicManhattanDistance;
	public int heuristicEuclideanDistance;
	public int heuristicHammingDistance;
	public int heuristicChebyshev;
	public int heuristicGaschnig;
	public int level;
	public int heuristicPermutationInversion;
	public int[][] goalState; // = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10,
								// 11, 0 } };
	public HashMap<Integer, ArrayList<Integer>> hashLocationGoalItems = new HashMap<>();

	public State(int[][] state, int[][] goal) {

		currentState = new int[state.length][state[0].length];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				currentState[i][j] = state[i][j];
			}
		}

		goalState = new int[goal.length][goal[0].length];
		for (int i = 0; i < goal.length; i++) {
			for (int j = 0; j < goal[0].length; j++) {
				goalState[i][j] = goal[i][j];
			}
		}

		for (int i = 0; i < goalState.length; i++) {
			for (int j = 0; j < goalState[i].length; j++) {
				ArrayList<Integer> loc = new ArrayList<>();
				loc.add(i);
				loc.add(j);
				hashLocationGoalItems.put(goalState[i][j], loc);
			}
		}
		
		this.setManhattanDistance();
		this.setEuclideanDistance();
		this.setHammingDistance();
		this.setPermutationInversion();
		this.setChebyshev();
		this.setGaschnig();
	}

	public boolean isGoalReached() {
		boolean isGoalState = true;

		for (int i = 0; i < this.currentState.length; i++) {
			for (int j = 0; j < this.currentState[0].length; j++) {
				if (this.currentState[i][j] != goalState[i][j]) {
					isGoalState = false;
					return isGoalState;
				}
			}
		}
		return isGoalState;
	}

	public void moveUp(int state[][], int zeroX, int zeroY) {

		if (zeroX >= 1) {
			int[][] childUp = new int[state.length][state[0].length];
			copyState(childUp, state);

			int temp = childUp[zeroX][zeroY];
			childUp[zeroX][zeroY] = childUp[zeroX - 1][zeroY];
			childUp[zeroX - 1][zeroY] = temp;

			State childUpState = new State(childUp, goalState);
			childUpState.parent = this;
			childUpState.level = this.level + 1;
			childUpState.setUpMove(childUpState);
			childrenStates.add(childUpState);
		}
	}

	public void moveUpRight(int state[][], int zeroX, int zeroY) {

		if (zeroX >= 1 && zeroY <= 2) {
			int[][] childUpRight = new int[state.length][state[0].length];
			copyState(childUpRight, state);

			int temp = childUpRight[zeroX][zeroY];
			childUpRight[zeroX][zeroY] = childUpRight[zeroX - 1][zeroY + 1];
			childUpRight[zeroX - 1][zeroY + 1] = temp;

			State childRightState = new State(childUpRight, goalState);
			childRightState.parent = this;
			childRightState.level = this.level + 1;
			childRightState.setUpRightMove(childRightState);
			childrenStates.add(childRightState);
		}
	}

	public void moveRight(int state[][], int zeroX, int zeroY) {

		if (zeroY <= 2) {
			int[][] childRight = new int[state.length][state[0].length];
			copyState(childRight, state);

			int temp = childRight[zeroX][zeroY];
			childRight[zeroX][zeroY] = childRight[zeroX][zeroY + 1];
			childRight[zeroX][zeroY + 1] = temp;

			State childRightState = new State(childRight, goalState);
			childRightState.parent = this;
			childRightState.level = this.level + 1;
			childRightState.setRightMove(childRightState);
			childrenStates.add(childRightState);
		}
	}

	public void moveDownRight(int state[][], int zeroX, int zeroY) {

		if (zeroX <= 1 && zeroY <= 2) {
			int[][] childDownRight = new int[state.length][state[0].length];
			copyState(childDownRight, state);

			int temp = childDownRight[zeroX][zeroY];
			childDownRight[zeroX][zeroY] = childDownRight[zeroX + 1][zeroY + 1];
			childDownRight[zeroX + 1][zeroY + 1] = temp;

			State childDownRightState = new State(childDownRight, goalState);
			childDownRightState.parent = this;
			childDownRightState.level = this.level + 1;
			childDownRightState.setDownRightMove(childDownRightState);
			childrenStates.add(childDownRightState);
		}
	}

	public void moveDown(int state[][], int zeroX, int zeroY) {

		if (zeroX <= 1) {
			int[][] childDown = new int[state.length][state[0].length];
			copyState(childDown, state);

			int temp = childDown[zeroX][zeroY];
			childDown[zeroX][zeroY] = childDown[zeroX + 1][zeroY];
			childDown[zeroX + 1][zeroY] = temp;

			State childDownState = new State(childDown, goalState);
			childDownState.parent = this;
			childDownState.level = this.level + 1;
			childDownState.setDownMove(childDownState);
			childrenStates.add(childDownState);
		}
	}

	public void moveDownLeft(int state[][], int zeroX, int zeroY) {

		if (zeroX <= 1 && zeroY >= 1) {
			int[][] childDownLeft = new int[state.length][state[0].length];
			copyState(childDownLeft, state);

			int temp = childDownLeft[zeroX][zeroY];
			childDownLeft[zeroX][zeroY] = childDownLeft[zeroX + 1][zeroY - 1];
			childDownLeft[zeroX + 1][zeroY - 1] = temp;

			State childDownLeftState = new State(childDownLeft, goalState);
			childDownLeftState.parent = this;
			childDownLeftState.level = this.level + 1;
			childDownLeftState.setDownLeftMove(childDownLeftState);
			childrenStates.add(childDownLeftState);
		}
	}

	public void moveLeft(int state[][], int zeroX, int zeroY) {

		if (zeroY >= 1) {
			int[][] childLeft = new int[state.length][state[0].length];
			copyState(childLeft, state);

			int temp = childLeft[zeroX][zeroY];
			childLeft[zeroX][zeroY] = childLeft[zeroX][zeroY - 1];
			childLeft[zeroX][zeroY - 1] = temp;

			State childLeftState = new State(childLeft, goalState);
			childLeftState.parent = this;
			childLeftState.level = this.level + 1;
			childLeftState.setLeftMove(childLeftState);
			childrenStates.add(childLeftState);
		}
	}

	public void moveUpLeft(int state[][], int zeroX, int zeroY) {

		if (zeroX >= 1 && zeroY >= 1) {
			int[][] childUpLeft = new int[state.length][state[0].length];
			copyState(childUpLeft, state);

			int temp = childUpLeft[zeroX][zeroY];
			childUpLeft[zeroX][zeroY] = childUpLeft[zeroX - 1][zeroY - 1];
			childUpLeft[zeroX - 1][zeroY - 1] = temp;

			State childUpLeftState = new State(childUpLeft, goalState);
			childUpLeftState.parent = this;
			childUpLeftState.level = this.level + 1;
			childUpLeftState.setUpLeftMove(childUpLeftState);
			childrenStates.add(childUpLeftState);
		}
	}

	public void copyState(int[][] target, int[][] source) {

		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < source[0].length; j++) {
				target[i][j] = source[i][j];
			}
		}

	}

	public boolean isCurrentStateRegenerated(int[][] state) {
		boolean isClone = true;

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				if (currentState[i][j] != state[i][j]) {
					isClone = false;
					return isClone;
				}
			}
		}

		return isClone;
	}

	public void printCurrentState() {
		System.out.print(getCharacterLocation() + " [");
		for (int i = 0; i < this.currentState.length; i++) {
			for (int j = 0; j < this.currentState[0].length; j++) {
				if (i == currentState.length - 1 && j == currentState[0].length - 1)
					System.out.print(currentState[i][j]);
				else
					System.out.print(currentState[i][j] + ", ");
			}
			// System.out.println();
		}
		//System.out.println("]" + " cheby = "+this.heuristicChebyshev + " level = " + this.level);
		System.out.println("]");
	}

	public void generateChildrenStatesDFS() {
		int zeroX = 0;
		int zeroY = 0;
		boolean breakOuterLoop = false;
		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				if (currentState[i][j] == 0) {
					zeroX = i;
					zeroY = j;
					breakOuterLoop = true;
					break;
				}
			}
			if (breakOuterLoop) {
				break;
			}
		}
		// to implement stack counter-clockwise
		moveUpLeft(currentState, zeroX, zeroY);
		moveLeft(currentState, zeroX, zeroY);
		moveDownLeft(currentState, zeroX, zeroY);
		moveDown(currentState, zeroX, zeroY);
		moveDownRight(currentState, zeroX, zeroY);
		moveRight(currentState, zeroX, zeroY);
		moveUpRight(currentState, zeroX, zeroY);
		moveUp(currentState, zeroX, zeroY);

	}

	public void generateChildrenStates() {
		int zeroX = 0;
		int zeroY = 0;
		boolean breakOuterLoop = false;
		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				if (currentState[i][j] == 0) {
					zeroX = i;
					zeroY = j;
					breakOuterLoop = true;
					break;
				}
			}
			if (breakOuterLoop) {
				break;
			}
		}

		moveUp(currentState, zeroX, zeroY);
		moveUpRight(currentState, zeroX, zeroY);
		moveRight(currentState, zeroX, zeroY);
		moveDownRight(currentState, zeroX, zeroY);
		moveDown(currentState, zeroX, zeroY);
		moveDownLeft(currentState, zeroX, zeroY);
		moveLeft(currentState, zeroX, zeroY);
		moveUpLeft(currentState, zeroX, zeroY);

	}

	public char getCharacterLocation() {
		int zeroX = 0;
		int zeroY = 0;
		boolean breakOuterLoop = false;
		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				if (currentState[i][j] == 0) {
					zeroX = i;
					zeroY = j;
					breakOuterLoop = true;
					break;
				}
			}
			if (breakOuterLoop) {
				break;
			}
		}

		if (zeroX == 0 && zeroY == 0)
			return 'a';
		else if (zeroX == 0 && zeroY == 1) {
			return 'b';
		} else if (zeroX == 0 && zeroY == 2) {
			return 'c';
		} else if (zeroX == 0 && zeroY == 3) {
			return 'd';
		} else if (zeroX == 1 && zeroY == 0) {
			return 'e';
		} else if (zeroX == 1 && zeroY == 1) {
			return 'f';
		} else if (zeroX == 1 && zeroY == 2) {
			return 'g';
		} else if (zeroX == 1 && zeroY == 3) {
			return 'h';
		} else if (zeroX == 2 && zeroY == 0) {
			return 'i';
		} else if (zeroX == 2 && zeroY == 1) {
			return 'j';
		} else if (zeroX == 2 && zeroY == 2) {
			return 'k';
		} else if (zeroX == 2 && zeroY == 3) {
			return 'l';
		}
		return ' ';
	}

	public void setUpMove(State child) {
		child.move = Move.Up;
		child.movePriority = 1;
	}

	public void setUpRightMove(State child) {
		child.move = Move.UpRight;
		child.movePriority = 2;
	}

	public void setRightMove(State child) {
		child.move = Move.Right;
		child.movePriority = 3;
	}

	public void setDownRightMove(State child) {
		child.move = Move.DownRight;
		child.movePriority = 4;
	}

	public void setDownMove(State child) {
		child.move = Move.Down;
		child.movePriority = 5;
	}

	public void setDownLeftMove(State child) {
		child.move = Move.DownLeft;
		child.movePriority = 6;
	}

	public void setLeftMove(State child) {
		child.move = Move.Left;
		child.movePriority = 7;
	}

	public void setUpLeftMove(State child) {
		child.move = Move.UpLeft;
		child.movePriority = 8;
	}

	public void setChebyshev() {
		this.heuristicChebyshev = chebyshevHeuristic(this.currentState);
	}
	
	public int chebyshevHeuristic(int initialState[][]) {
		int chebDist = 0;

		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[i].length; j++) {
				int item = initialState[i][j];
				if (item == 0)
					continue;
				ArrayList<Integer> location = hashLocationGoalItems.get(item);
				int x = location.get(0);
				int y = location.get(1);

				int rowDiff = Math.abs(x - i);
				int colDiff = Math.abs(y - j);
				chebDist += Math.max(rowDiff, colDiff);

			}
		}

		return chebDist;

	}

	public void setGaschnig() {
		this.heuristicGaschnig = gaschnigHeuristic(this.currentState);
	}
	
	public int gaschnigHeuristic(int initialState[][]) {
		int gaschDist = 0;

		int[][] initState = new int[initialState.length][initialState[0].length];
		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[0].length; j++) {
				initState[i][j] = initialState[i][j];
			}
		}
		
		
		while (!isGoalReachedCustom(initState)) {
			int[] pos = findElement(initState, 0);
			int posZeroX = pos[0];
			int posZeroY = pos[1];

			if ((posZeroX == initState.length - 1) && (posZeroY == initState[0].length - 1)) {
				int k = 1;
				int posItemX = 0;
				int posItemY = 0;
				boolean breakOuter = false;
				for (int i = 0; i < initState.length; i++) {
					for (int j = 0; j < initState[i].length; j++) {
						if (initState[i][j] != k) {
							posItemX = i;
							posItemY = j;
							breakOuter = true;
							break;
						}
						k++;
					}
					if (breakOuter) {
						break;
					}
				}
				int temp = initState[posZeroX][posZeroY];
				initState[posZeroX][posZeroY] = initState[posItemX][posItemY];
				initState[posItemX][posItemY] = temp;
				gaschDist++;
			} else {
				int itemInGoal = goalState[posZeroX][posZeroY];
				int[] item = findElement(initState, itemInGoal);
				int itemX = item[0];
				int itemY = item[1];
				// swap
				int temp = initState[posZeroX][posZeroY];
				initState[posZeroX][posZeroY] = initState[itemX][itemY];
				initState[itemX][itemY] = temp;
				gaschDist++;
			}
		}

		return gaschDist;
	}
	
	public int[] findElement(int[][] state, int x) {
		int[] loc = new int[2];
		int posX = 0;
		int posY = 0;
		boolean found = false;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j] == x) {
					posX = i;
					posY = j;
					found = true;
					break;
				}
			}

			if (found) {
				break;
			}
		}
		loc[0] = posX;
		loc[1] = posY;

		return loc;
	}

	public boolean isGoalReachedCustom(int currentState[][]) {
		boolean isGoalState = true;

		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				if (currentState[i][j] != this.goalState[i][j]) {
					isGoalState = false;
					return isGoalState;
				}
			}
		}
		return isGoalState;
	}


	
	public void setManhattanDistance() {
		this.heuristicManhattanDistance = manhattanDistance(this.currentState);
	}

	public int manhattanDistance(int initialState[][]) {
		int dist = 0;
		int manDist = 0;
		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[i].length; j++) {
				int item = initialState[i][j];
				if (item == 0)
					continue;
				ArrayList<Integer> location = hashLocationGoalItems.get(item);
				int x = location.get(0);
				int y = location.get(1);
				dist = Math.abs(x - i) + Math.abs(y - j);
				manDist += dist;
			}
		}
		return manDist;
	}

	public void setEuclideanDistance() {
		this.heuristicEuclideanDistance = euclideanDistance(this.currentState);
	}

	public int euclideanDistance(int initialState[][]) {
		int dist = 0;
		int eucDist = 0;
		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[i].length; j++) {
				int item = initialState[i][j];
				if (item == 0)
					continue;
				ArrayList<Integer> location = hashLocationGoalItems.get(item);
				int x = location.get(0);
				int y = location.get(1);
				int distX = Math.abs(x - i);
				int distY = Math.abs(y - j);
				int distXY = distX * distX + distY * distY;
				dist = (int) Math.sqrt(distXY);
				eucDist += dist;
			}
		}
		return eucDist;
	}

	public void setHammingDistance() {
		this.heuristicHammingDistance = hammingDistance(this.currentState);
	}

	public static int hammingDistance(int initialState[][]) {
		int count = 0;
		int k = 1;
		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[i].length; j++) {
				int item = initialState[i][j];
				if (item == 0) {
					k++;
					continue;
				}

				if (item != k)
					count++;
				// System.out.println("k = "+ k + " count = "+count + "item =
				// "+item);
				k++;

			}
		}
		return count;
	}

	public void setPermutationInversion() {
		this.heuristicHammingDistance = hammingDistance(this.currentState);
	}

	public int permutationInversion(int[][] matrix) {
		int permInv = 0;
		int row = matrix.length;
		int col = matrix[0].length;
		int arr[] = new int[row * col];

		int k = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				arr[k] = matrix[i][j];
				k++;
			}
		}
		for (int i = 0; i < arr.length - 1; i++) {
			int item = arr[i];
			if (item == 0)
				continue;
			int count = 0;
			System.out.println("Item : " + item);
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] == 0)
					continue;
				if (item > arr[j])
					count++;
			}
			System.out.println(count);
			permInv += count;
		}

		return permInv;
	}
}
