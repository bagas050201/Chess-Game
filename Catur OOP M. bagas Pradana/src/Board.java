public class Board {
	
	Piece[][] board;//membuat array kosong sebagai lokasi bidak nanti
	
	
	
	public Board(){
		board = new Piece[8][8]; // buat papan berukuran 8x8
		
		initializeBoard();
		
	}
	
	
	public void initializeBoard(){//bikin papan
		//initialize the board
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				board[i][j] = null;
			}
		}
		
		
		//initialize potongan/pieces dan letakan mereka dalam papan
		//white
		board[0][1] = new Pawn(false);
		board[1][1] = new Pawn(false);
		board[2][1] = new Pawn(false);
		board[3][1] = new Pawn(false);
		board[4][1] = new Pawn(false);
		board[5][1] = new Pawn(false);
		board[6][1] = new Pawn(false);
		board[7][1] = new Pawn(false);
		
		board[0][0] = new Rook(false);
		board[1][0] = new Knight(false);
		board[2][0] = new Bishop(false);
		board[3][0] = new Queen(false);
		board[4][0] = new King(false);
		board[5][0] = new Bishop(false);
		board[6][0] = new Knight(false);
		board[7][0] = new Rook(false);
		
		//black
		board[0][6] = new Pawn(true);
		board[1][6] = new Pawn(true);
		board[2][6] = new Pawn(true);
		board[3][6] = new Pawn(true);
		board[4][6] = new Pawn(true);
		board[5][6] = new Pawn(true);
		board[6][6] = new Pawn(true);
		board[7][6] = new Pawn(true);
		
		board[0][7] = new Rook(true);
		board[1][7] = new Knight(true);
		board[2][7] = new Bishop(true);
		board[3][7] = new Queen(true);
		board[4][7] = new King(true);
		board[5][7] = new Bishop(true);
		board[6][7] = new Knight(true);
		board[7][7] = new Rook(true);
	
		
		
	}
	
	public boolean isPathClear(int oldX, int oldY, int newX, int newY){//mengecek jalur catur 
		
		
		
		String pieceName = board[oldX][oldY].drawPiece();
		
		if (pieceName.equalsIgnoreCase("wk") || pieceName.equalsIgnoreCase("bk")) {
			return true;
		} else if (pieceName.equalsIgnoreCase("wn") || pieceName.equalsIgnoreCase("bn")) {
			return true;
		}
		
		int deltaX;
		int deltaY;
		
		deltaX = newX-oldX;
		deltaY = newY-oldY;
		
		int tempx = oldX;
		int tempy = oldY;
		int dx = 0;
		int dy = 0;
		
		if (deltaX > 0) {
			dx = 1;
		}else if (deltaX < 0) {
			dx = -1;
		}
		
		if (deltaY > 0) {
			dy = 1;
		}else if (deltaY < 0) {
			dy = -1;
		}
		
		
		boolean clearPath = true;
		
		
		
		if (deltaY == 0) {
			tempx = tempx + dx;
			for (int i = 0; i < Math.abs(deltaX)-1; i++) {
				if (board[tempx][tempy] != null) {
					clearPath = false;
					break;
				}
				
				tempx = tempx + dx;
			} 
			return clearPath;
		}
		
		
		if (deltaX == 0) {
			tempy = tempy + dy;
			for (int i = 0; i < Math.abs(deltaY)-1; i++) {
				if (board[tempx][tempy] != null) {
					clearPath = false;
					break;
				}
				
				tempy = tempy + dy;
			}
			
			return clearPath;
		}
		
		if (deltaX != 0 && deltaY != 0) {
			tempx = tempx + dx;
			tempy = tempy + dy;
			for (int i = 0; i < Math.abs(deltaY)-1; i++) {
				if (board[tempx][tempy] != null) {
					clearPath = false;
					break;
				}
				
				tempx = tempx + dx;
				tempy = tempy + dy;
			} 
		}
		
		return clearPath;
		
	}
	
	public boolean testCastling(int oldX, int oldY, int newX, int newY) {//membuat teknik Rokade pada permainan ini

		int deltax = newX - oldX;
		if ( board[oldX][oldY] != null) {
			
			String pieceName = board[oldX][oldY].drawPiece();
			if (pieceName.equalsIgnoreCase("wk") && board[oldX][oldY].firstMove == true) { //mengecek bahwa king white bisa dijalankan
				
				if (deltax == 2) { // apakah dia ingin rokade dikanan?

					if (board[7][7] != null) {
						if (board[7][7].drawPiece().equalsIgnoreCase("wr") ) { //check jika benteng ada ditempat awal

							if (isPathClear(oldX, oldY, newX, newY)) {//menghapus lokasi lama karena pieces dipindahkan
								board[oldX][oldY] = null;
								board[7][7] = null;
								
								board[newX][newY] = new King(true); // eksekusi teknik rokade
								board[newX][newY].firstMove = false;
								
								board[5][7] = new Rook(true);
								board[5][7].firstMove = false;
								
								return true;
							}
						}
					}
				}
			}
		}
		
		if ( board[oldX][oldY] != null) {
			String pieceName = board[oldX][oldY].drawPiece();

			if (pieceName.equalsIgnoreCase("bk") && board[oldX][oldY].firstMove == true) { //jika raja hitam bisa bergerak

				if (deltax == 2) { // apakah dia ingin rokade dikanan?
					if (board[7][0] != null) { //apakah benteng berada dilokasi rokade?
						if (board[7][0].drawPiece().equalsIgnoreCase("br") ) { //check apakah benteng ada diloakasi awal
							if (isPathClear(oldX, oldY, newX, newY)) {////menghapus lokasi lama karena pieces dipindahkan
								board[oldX][oldY] = null;
								board[7][0] = null;
								
								board[newX][newY] = new King(false);//eksekusi teknik rokade
								board[newX][newY].firstMove = false;
								
								board[5][0] = new Rook(false);
								board[5][0].firstMove = false;
								
								return true;
							}
						}
					}
				}
			}
		}
		
		if ( board[oldX][oldY] != null) {
			String pieceName = board[oldX][oldY].drawPiece();

			if (pieceName.equalsIgnoreCase("wk") && board[oldX][oldY].firstMove == true) { //jika raja putih bisa bergerak

				if (deltax == -2) { // apakah dia ingin rokade  di kiri?

					if (board[0][7] != null) { // periksa apakah benteng masih ada

						if (board[0][7].drawPiece().equalsIgnoreCase("wr") ) { //check apakah benteng ada dilokasi awal

							if (isPathClear(oldX, oldY, newX, newY)) {////menghapus lokasi lama karena pieces dipindahkan

								board[oldX][oldY] = null; //hilangkan raja
								board[0][7] = null; //hilangkan benteng
								
								board[newX][newY] = new King(true); // eksekusi teknik rokade
								board[newX][newY].firstMove = false;
								
								board[3][7] = new Rook(true);
								board[3][7].firstMove = false;

								
								return true;
							}
						}
					}
				}
			}
		}
		
		if ( board[oldX][oldY] != null) {
			String pieceName = board[oldX][oldY].drawPiece();

			if (pieceName.equalsIgnoreCase("bk") && board[oldX][oldY].firstMove == true) { //jika raja hitam tidak bisa bergerak


				if (deltax == -2) { // rokade sebelah kiri

					if (board[0][0] != null) { // check apakah benteng terkena rokade

						if (board[0][0].drawPiece().equalsIgnoreCase("br") ) { //check apakh benteng berada dilokasi awal

							if (isPathClear(oldX, oldY, newX, newY)) {////menghapus lokasi lama karena pieces dipindahkan

								board[oldX][oldY] = null; //hilangkan raja
								board[0][0] = null; //hilangkan benteng
								
								board[newX][newY] = new King(false); // eksekusi teknik rokade
								board[newX][newY].firstMove = false;
								
								board[3][0] = new Rook(false);
								board[3][0].firstMove = false;
								
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	public boolean detectCheck(boolean whiteturn) {
	int kingLocX = 0;
	int kingLocY = 0;
	
		
	if (whiteturn == true) {//jika giliran pertama si putih, maka di cek lokasi raja putih ada dimana, dan simpan lokasi tersebut.
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){//men scan seluruh bagian isi papan koordinat x dan y
				if (board[x][y] != null) {//jika dalam isi papan ternyata tidak kosong, jika true maka
					if (board[x][y].drawPiece().equalsIgnoreCase("wk")) {//jika dalam isi papan tersebut ditemukan kata "wk" atau tanda dari piece raja
						//menyimpan lokasi raja putih berupa koordinat papan
						kingLocX = x;
						kingLocY = y;
						break;
					}
				}
			}
		}
		
		for (int y = 0; y < 8; y++){//men scan kembali seluruh bagian isi papan koordinat x dan y
			for (int x = 0; x < 8; x++){
				if (board[x][y] != null) {//jika dalam isi papan ternyata tidak kosong, jika true maka
					if (board[x][y].isWhite() == false) {//giliran pertama sebelumnya adalah white, namun di bagian sini, turn selanjutnya pasti giliran black, maka jika true
						if (board[x][y].canMove(x, y, kingLocX, kingLocY, true)) {// si pemain black/bidak black akan men scan seluruh langkah yang dapat dia lakukan, apakah bisa ke x, ke y, ke lokasi koordinat X dan Y raja putih sebelumnya, jika true maka
							if (isPathClear(x, y, kingLocX, kingLocY)) {//jika langkah white king di turn selanjutnya masih bisa bergerak,jika true maka
								return true;//syarat terjadi check terpenuhi
							}
						}
					}
				}
			}
		}
	}
	
	
	if (whiteturn == false) {//jika giliran pertama si hitam, maka di cek lokasi raja hitam ada dimana, dan simpan lokasi tersebut.
		for (int y = 0; y < 8; y++){//men scan seluruh bagian isi papan koordinat x dan y
			for (int x = 0; x < 8; x++){
				if (board[x][y] != null) {//jika dalam isi papan ternyata tidak kosong, jika true maka
					if (board[x][y].drawPiece().equalsIgnoreCase("bk")) {//jika dalam isi papan tersebut ditemukan kata "bk" atau tanda dari piece raja
						//menyimpan lokasi raja hitam berupa koordinat papan
						kingLocX = x;
						kingLocY = y;
						break;
					}
				}
			}
		}
		
		for (int y = 0; y < 8; y++){//men scan kembali seluruh bagian isi papan koordinat x dan y
			for (int x = 0; x < 8; x++){
				if (board[x][y] != null) {//jika dalam isi papan ternyata tidak kosong, jika true maka
					if (board[x][y].isWhite() == true) {//giliran pertama sebelumnya adalah hitam, namun di bagian sini, turn selanjutnya pasti giliran putih, maka jika true
						if (board[x][y].canMove(x, y, kingLocX, kingLocY, true)) {// si pemain white/bidak white akan men scan seluruh langkah yang dapat dia lakukan, apakah bisa ke x, ke y, ke lokasi koordinat X dan Y raja hitam sebelumnya, jika true maka
							if (isPathClear(x, y, kingLocX, kingLocY)) {//jika langkah Black king di turn selanjutnya masih bisa bergerak,jika true maka
								return true;//syarat terjadinya Check terpenuhi
							}
						}
					}
				}
			}
		}
	}
	

	return false;
	}
}