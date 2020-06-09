public abstract class Piece {
	
	// jika player adalah white maka true
	private boolean white = true;
	
	public boolean firstMove = true;
	
	//Setel bagian menjadi milik pemain putih atau hitam. Putih direpresentasikan sebagai True, hitam diwakili sebagai False. 
	public void setWhite(boolean t){
		white = t;
	}
	
	
	
    //memberitahu apakah piece tersebut putih atau hitam. jika putih maka true, jika hitam maka false.
	public boolean isWhite(){
		return this.white;
	}
	
	
	
	public abstract boolean canMove(int oldX, int oldY, int newX, int newY, boolean isNewSpotEmpty);
	
	public abstract void movePiece();
	
	public abstract String drawPiece();
}
