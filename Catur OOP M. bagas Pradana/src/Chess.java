import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * BufferedReader = mengambil imput/membaca file dari keyboard/input kemudian mengambil hasil tersebut untuk diproses 
 * IOexception = sebagai penyedia penanganan terhadap eror atau kesalahan,terdiri atas try,catch,finally. namun yang hanya digunakan disini adalah Try{} dan Catch{}.
 * InputStreamReader = berfungsi sebagai input dan hasil data tersebut dalam bentuk variabel
 * StringTokenizer = berfungsi sebagai pemecah String yang panjang menjadi beberapa bagian kata string
*/

public class Chess {
	
	public static Board gameboard;
	public static String currentLoc = null;
	public static String newLoc = null;
	public static String thirdArgument = null;
	public static boolean askForDraw  = false;
	public static boolean whiteTurn = true;
	
	
	public static void main(String[] args) {
		//initialize permainan
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		System.out.println("{---------------[Game Catur Berbasis OOP]---------------}");
		System.out.println("Nama  = Muhammad Bagas Pradana(1313618015/2018)");
		System.out.println("Prodi = Ilmu Komputer");
		System.out.println("masukan input seperti = (lokasi awal bidak) (lokasi tujuan bidak)");
		System.out.println("-----{Selamat bermain}-----");
		
		 
		initializeGame();
		drawBoard();
		
		//loop dalam permainan
		while (true){
			//membaca pergerakan pemain
		     input = null;
		    
		     try {
		    	 
		    	 if (whiteTurn){
		    		 System.out.println();
		    			System.out.println("masukan input untuk White player's :");	
		    	 }else{
		    		 System.out.println();
		    		 System.out.println("masukan input untuk Black player's :");
		    	 }
		    	 boolean check = gameboard.detectCheck(whiteTurn);
		    	 if (check == true) {
		    		 if (whiteTurn) {
		    			System.out.println("white king in Check");
		    		 }else {
		    			System.out.println("black king in Check");
		    		 }
		    	 }
		    
		    	
		    	 input = br.readLine();
		    	 
		    	 parseInput(input);
		  
		    	 drawBoard();
		    	 
		    	 	 
		    	 
		     } catch (IOException e) {//jika input tidak sesuai
		    	 System.out.println("Invalid input. Try again.");
		     }       
		}
		
		//keluar
	}
	
	public static void initializeGame(){
		gameboard = new Board();
	}
	
	
	public static void resignGame(){
		
	}
	
	
	public static void gameDraw(){
		
	}
	
	public static void drawBoard(){
		String[][] result = new String[8][8];
		
		//membuat tiles dengan warna putih atau hitam
		boolean white = true;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (white){
					result[i][j] = "  |";
					white = false;
				}else{
					result[i][j] = "##|";
					white = true;
				}
			}
			white = !(white);
		}	
		
		// meletakan potongan pieces di lokasi yang tepat
		for (int y = 0; y < 8; y++){
			for (int x = 0; x < 8; x++){
				
				if ( gameboard.board[x][y] != null){
					result[x][y] = gameboard.board[x][y].drawPiece() + "|";
				}
			}
		}
		
		//print seluruh bagian papan  
		System.out.println("_________________________");
		for (int y = 0; y < 8; y++){
			System.out.print("|");
			for (int x = 0; x < 8; x++){
				System.out.print(result[x][y]);
			}
			System.out.print("  " + (8-y));
			System.out.println();
		}
		System.out.println("\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'"); 
		System.out.println(" a  b  c  d  e  f  g  h"); 
	}
	
	
	
	public static int fileToCoordinate(String file){//pengubah imput menjadi lower case
		int result = (int) file.toLowerCase().charAt(0) - (int)('a');
		return result;
	}
	
	
	
	public static int rankToCoordinate(String file){
		int result = 7 - ((int) file.toLowerCase().charAt(1) - (int)('1'));
		//System.out.println("Yeet");
		return result;
	}

	
	
	public static boolean legalInput(int x1, int x2, int x3, int x4){
		if ((x1 >= 0 && x1 <= 7) && (x2 >= 0 && x2 <= 7) && (x3 >= 0 && x3 <= 7) && (x4 >= 0 && x4 <= 7)){
			return true;
		}
		
		return false;
		
	}
	
	
	public static void parseInput(String input){
		StringTokenizer st = null;
		int count = 0;
		input = input.toLowerCase();
		input = input.trim();
		
		String[] array = new String[100]; 
		 
		st = new StringTokenizer(input);
		 
		while (st.hasMoreTokens()) {
	        array[count] = st.nextToken();
	        count++;
	    }
		
		if (count > 0 && count < 4){
			if (count == 1){
				if (array[0].equals("resign")){
					if (whiteTurn){
						System.out.println("Black wins");
						System.exit(1);
					}else{
						System.out.println("White wins");
						System.exit(1);
					}
					
				}else if (array[0].equals("draw")){
					if (askForDraw == true){
						System.out.println("Draw");
						System.exit(1);
					}else{
						System.out.println("bertanya kepada lawan , bahwa apakah dia ingin menyebut permainan ini ingin seri atau tidak? terlebih dahulu.");
					}
				}else{
					System.out.println("Invalid input. Please try again. ");
				}
			}else if (count == 2) {
				currentLoc = array[0];
				newLoc = array[1];
				if (currentLoc.length() == 2 && newLoc.length() == 2 ){
					
					executeMove();
				}else{
					System.out.println("Invalid input. Please try again.");
				}
				
			}else if (count == 3){
				currentLoc = array[0];
				newLoc = array[1];
				if (currentLoc.length() == 2 && newLoc.length() == 2 ){
					
					if (array[2].equals("draw?")){
						executeMove();
						askForDraw = true;
					}else if (array[2].equals("q") || array[2].equals("r") || array[2].equals("b") || array[2].equals("n")) {
						thirdArgument = array[2];
						executeMove();
					}else{
						System.out.println("Invalid input. Please try again.");
					}
				}else{
					System.out.println("Invalid input. Please try again.");
				}
			}
		}else{
			System.out.println("Invalid input.Please try again.");
		}
		
		currentLoc = null;
		newLoc = null;
		thirdArgument = null;
	}
	
	
	
	public static void executeMoveOld(){
		 
		int oldx = fileToCoordinate(currentLoc);
		int oldy =rankToCoordinate(currentLoc);
		 
		int newx = fileToCoordinate(newLoc);
		int newy =rankToCoordinate(newLoc);
		
		System.out.println(currentLoc +" "+ newLoc);
		 
		if (legalInput(oldx, oldy, newx, newy) == true){ //mengecek apakah koordinat yang dimasukan telah sah?
			
			if (gameboard.board[oldx][oldy] != null){ // mengecek apakah terdapat sesuatu di lokasi yang dipilih
				
				//if (gameboard.board[oldx][oldy].canMove(oldx, oldy, newx, newy, false)){
						
					if ((whiteTurn && gameboard.board[oldx][oldy].isWhite()) || (!whiteTurn && !gameboard.board[oldx][oldy].isWhite())){ //mengecek apakah bagian yang dipindahkan milik pemain yang saat ini gilirannya
						if (gameboard.board[newx][newy] == null){ // mengecek apakah di lokasi terbaru memang kosong
							
							if (gameboard.testCastling(oldx, oldy, newx, newy)) {
								return;
							}
							
							if (gameboard.board[oldx][oldy].canMove(oldx, oldy, newx, newy, true)  && gameboard.isPathClear(oldx, oldy, newx, newx) ){ 
								
								gameboard.board[newx][newy] = gameboard.board[oldx][oldy];
								
								if ( gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("wp") || gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("bp")  ) {
									pawnPromotion(newx, newy);
								}
								
								gameboard.board[oldx][oldy] = null;
								
								whiteTurn = !whiteTurn;
							}else{
								System.out.println("Illegal move: This piece cannot move this way.");
							}
							
						}else if ((whiteTurn && !gameboard.board[newx][newy].isWhite()) || 
								(!whiteTurn && gameboard.board[newx][newy].isWhite())){ // apakah dibagian warna lama ternyata benar dan apakah benar saat ditempat yang baru juga?
							if (gameboard.board[oldx][oldy].canMove(oldx, oldy, newx, newy, false)  && gameboard.isPathClear(oldx, oldy, newx, newx)){ 
								
								gameboard.board[newx][newy] = gameboard.board[oldx][oldy];
								
								if ( gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("wp") || gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("bp")  ) {
									pawnPromotion(newx, newy);
								}
								
								gameboard.board[oldx][oldy] = null;
								
								whiteTurn = !whiteTurn;
							}else{
									System.out.println("Illegal move: bidak ini tidak dapat bergerak kearah sini.");
							}
							
						}else{
							 System.out.println("Illegal move. Please try again..??");
						}
					
				}else{
					 System.out.println("Illegal move: bidak ini tidak dapat bergerak kesini. Please try again.");
				}
			}else{
				System.out.println("Invalid move: tidak ada bidak di lokasi yang anda pilih. Please try again.");
			}
		}else{
			System.out.println("Invalid move: lokasi yang anda pilih tidak terdapat dalam papan. Please try again.");
		}
		 
	}
	
	
	public static void pawnPromotion(int newx, int newy) {//saat menaikan jabatan pawn saat telah berada diujung papan
		if (gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("wp") && newy == 0) {
			//buat minta input lgi dan input nyasar dihapus
			if (thirdArgument == null) {
				gameboard.board[newx][newy] = new Queen(true);
				
			}else if (thirdArgument.equals("q")) {
				gameboard.board[newx][newy] = new Queen(true);
				
			}else if (thirdArgument.equals("r")) {
				gameboard.board[newx][newy] = new Rook(true);
				
			}else if (thirdArgument.equals("b")) {
				gameboard.board[newx][newy] = new Bishop(true);
				
			}else if (thirdArgument.equals("n")) {
				gameboard.board[newx][newy] = new Knight(true);
				
			}
		}else if (gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("bp") && newy == 7) {
			if (thirdArgument == null) {
				gameboard.board[newx][newy] = new Queen(false);
				
			}else if (thirdArgument.equals("q")) {
				gameboard.board[newx][newy] = new Queen(false);
				
			}else if (thirdArgument.equals("r")) {
				gameboard.board[newx][newy] = new Rook(false);
				
			}else if (thirdArgument.equals("b")) {
				gameboard.board[newx][newy] = new Bishop(false);
				
			}else if (thirdArgument.equals("n")) {
				gameboard.board[newx][newy] = new Knight(false);
				
			}
		}
	}
	
	
	public static void executeMove(){
		 
		int oldx = fileToCoordinate(currentLoc);
		int oldy =rankToCoordinate(currentLoc);
		 
		int newx = fileToCoordinate(newLoc);
		int newy =rankToCoordinate(newLoc);
		
		
		
		
		if (legalInput(oldx, oldy, newx, newy) == false){ //apakah lokasi yang di input legal?
			System.out.println("Invalid file and rank. Please try again.");
			return;
		}
		
		if (gameboard.board[oldx][oldy] == null){ // apakah ada bidak lain di bagian spot yang dipilih?
			System.out.println("sebuah bidak tidak ditemukan di tempat ini. Please try again.");
			return;
		}
		
		
		if (whiteTurn != gameboard.board[oldx][oldy].isWhite()) {
			System.out.println("bagian bidak yang ingin anda coba pindahkan ternyata bukan milik anda. Please try again.");
			return;
		}
		
		boolean isNewSpotEmpty = true;
		if (gameboard.board[newx][newy] != null) {
			if (gameboard.board[newx][newy].isWhite() == whiteTurn) {
				System.out.println("tidak dapat maju ke lokasi yang telah diambil oleh bidak dengan warna yang sama. Please Try again.");
				return;
			}
			isNewSpotEmpty = false;
		}
		
		if (gameboard.testCastling(oldx, oldy, newx, newy)) {
			gameboard.board[newx][newy].firstMove = false;
			
			whiteTurn = !whiteTurn;
			return;
		}
		
		
		if (gameboard.isPathClear(oldx, oldy, newx, newy) == false) {
			System.out.println("tidak dapat bergerak ke lokasi tersebut karena dilokasi tersebut ada bidak yang mengisi.");
			return;
		}
		 
		if (gameboard.board[oldx][oldy].canMove(oldx, oldy, newx, newy, isNewSpotEmpty) == false) {
			System.out.println("Illegal move. bidak yang anda jalankan tidak dapat bergerak ke arah sini.");
			return;
		}
		
		
		
		gameboard.board[newx][newy] = gameboard.board[oldx][oldy];//memindahkan piece ketempat baru
		//no longer the first move of the piece
		gameboard.board[newx][newy].firstMove = false;
		
		if ( gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("wp") || gameboard.board[newx][newy].drawPiece().equalsIgnoreCase("bp")  ) {
			pawnPromotion(newx, newy);
		}
		
		gameboard.board[oldx][oldy] = null;
		
		
		
		
		//gameboard.detectCheckMate();
		whiteTurn = !whiteTurn;
		
		return;
		
	}
}
