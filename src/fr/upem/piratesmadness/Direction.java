package fr.upem.piratesmadness;

public enum Direction {
	NORTH {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==SOUTH)return true;
			return false;
		}
	}
	,EAST {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==WEST) return true;
			return false;
		}
	}
	,WEST {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==EAST) return true;
			return false;
		}
	}
	,SOUTH {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==NORTH) return true;
			return false;
		}
	};
	

	abstract public boolean isOpposite(Direction d);
}
