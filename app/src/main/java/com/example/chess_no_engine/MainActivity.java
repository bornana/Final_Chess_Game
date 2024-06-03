package com.example.chess_no_engine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Paint paint;
    FrameLayout frame;
    private BoardGame boardGame;
    Piece[][] pieces;
    Piece SavePiece;
    int SaveY = 0, SaveX = 0;
    int SaveI = 0, SaveJ = 0;
    ImageView iv;
    LinearLayout linear;
    RelativeLayout myRelativeLayout;
    private MusicService musicService;
    private boolean isServiceBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            isServiceBound = true;

            // Start or resume music when the service is connected
            if (!musicService.isMusicPlaying()) {
                musicService.resumeMusic();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            isServiceBound = false;
        }
    };
    boolean IsClicked = false;
    int turn = 0;
    boolean DidPromote = false, DidCastle = false;
    King king;
    ImageButton PBQ, PBR, PBKN, PBB, PWR, PWB, PWQ, PWKN;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout.LayoutParams layoutParams = NewLayout();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        frame = findViewById(R.id.frm);
        PBQ = findViewById(R.id.PBQ);
        PBQ.setClickable(false);
        PBQ.setVisibility(View.INVISIBLE);

        PBB = findViewById(R.id.PBB);
        PBB.setClickable(false);
        PBB.setVisibility(View.INVISIBLE);

        PBR = findViewById(R.id.PBR);
        PBR.setClickable(false);
        PBR.setVisibility(View.INVISIBLE);

        PBKN = findViewById(R.id.PBKN);
        PBKN.setClickable(false);
        PBKN.setVisibility(View.INVISIBLE);

        PWQ = findViewById(R.id.PWQ);
        PWQ.setClickable(false);
        PWQ.setVisibility(View.INVISIBLE);

        PWR = findViewById(R.id.PWR);
        PWR.setClickable(false);
        PWR.setVisibility(View.INVISIBLE);

        PWB = findViewById(R.id.PWB);
        PWB.setClickable(false);
        PWB.setVisibility(View.INVISIBLE);

        PWKN = findViewById(R.id.PWKN);
        PWKN.setClickable(false);
        PWKN.setVisibility(View.INVISIBLE);

        boardGame = new BoardGame(this);

        frame.addView(boardGame);
        //iv.setVisibility(View.INVISIBLE);
        pieces = new Piece[8][8];
        SavePiece = new Piece(5, 5, pieces,"gray", null);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //**rook**\\
        iv = findViewById(R.id.imageView14);
        myRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        //iv.setImageResource(R.drawable.blackroock);
        pieces[0][0] = new Rook(0,0,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][0].GetImageView(), layoutParams);
        pieces[0][0].GetImageView().setClickable(false);
        // iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.blackroock);
        pieces[0][7] = new Rook(0,7,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][7].GetImageView(), layoutParams);
        pieces[0][7].GetImageView().setClickable(false);
        // iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.whiteroock);
        pieces[7][0] = new Rook(7,0,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][0].GetImageView(), layoutParams);
        pieces[7][0].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.whiteroock);
        pieces[7][7] = new Rook(7,7,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][7].GetImageView(), layoutParams);
        pieces[7][7].GetImageView().setClickable(false);

        //**knight**\\
        // iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.blacknight);
        pieces[0][1] = new Knight(0,1,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][1].GetImageView(), layoutParams);
        pieces[0][1].GetImageView().setClickable(false);
        // iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.blacknight);
        pieces[0][6] = new Knight(0,6,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][6].GetImageView(), layoutParams);
        pieces[0][6].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        // iv.setImageResource(R.drawable.whitenight);
        pieces[7][1] = new Knight(7,1,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][1].GetImageView(), layoutParams);
        pieces[7][1].GetImageView().setClickable(false);
        // iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.whitenight);
        pieces[7][6] = new Knight(7,6,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][6].GetImageView(), layoutParams);
        pieces[7][6].GetImageView().setClickable(false);
        //**bishop**\\
        // iv = new ImageView(this);
        // iv.setImageResource(R.drawable.avram);
        pieces[0][2] = new Bishop(0,2,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][2].GetImageView(), layoutParams);
        pieces[0][2].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        // iv.setImageResource(R.drawable.avram);
        pieces[0][5] = new Bishop(0,5,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][5].GetImageView(), layoutParams);
        pieces[0][5].GetImageView().setClickable(false);
        // iv = new ImageView(this);
        // iv.setImageResource(R.drawable.whithbishop);
        pieces[7][2] = new Bishop(7,2,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][2].GetImageView(), layoutParams);
        pieces[7][2].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        // iv.setImageResource(R.drawable.whithbishop);
        pieces[7][5] = new Bishop(7,5,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][5].GetImageView(), layoutParams);
        pieces[7][5].GetImageView().setClickable(false);
        //**queen**\\
        //   iv = new ImageView(this);
        // iv.setImageResource(R.drawable.blackqueen);
        //pieces[0][3] = new Queen(0,3,pieces, "black", new ImageView(MainActivity.this));
        pieces[0][3] = new Queen(0,3,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][3].GetImageView(), layoutParams);
        pieces[0][3].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        // iv.setImageResource(R.drawable.elisabethrip);
        pieces[7][3] = new Queen(7,3,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][3].GetImageView(), layoutParams);
        pieces[7][3].GetImageView().setClickable(false);
        //**king**\\
        //   iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.tachalarip);
        pieces[0][4] = new King(0,4,pieces, "black", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[0][4].GetImageView(), layoutParams);
        pieces[0][4].GetImageView().setClickable(false);
        //  iv = new ImageView(this);
        //  iv.setImageResource(R.drawable.kingcharles);
        pieces[7][4] = new King(7,4,pieces, "white", new ImageView(MainActivity.this));
        layoutParams = NewLayout();
        layoutParams.width = width/16;
        layoutParams.height = width/16;
        layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        myRelativeLayout.addView(pieces[7][4].GetImageView(), layoutParams);
        pieces[7][4].GetImageView().setClickable(false);

        //**pawn**\\
        for (int j = 0; j<8; j++){
            // iv = new ImageView(this);
            //iv.setImageResource(R.drawable.blackpawn);
            pieces[1][j] = new Pawn(1,j,pieces, "black", new ImageView(MainActivity.this));
            layoutParams = NewLayout();
            layoutParams.width = width/16;
            layoutParams.height = width/16;
            layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            myRelativeLayout.addView(pieces[1][j].GetImageView(), layoutParams);
        }
        for (int j = 0; j<8; j++){
            //   iv = new ImageView(this);
            // iv.setImageResource(R.drawable.whitepawn);
            pieces[6][j] = new Pawn(6,j,pieces, "white", new ImageView(MainActivity.this));
            layoutParams = NewLayout();
            layoutParams.width = width/16;
            layoutParams.height = width/16;
            layoutParams.addRule(RelativeLayout.BELOW, R.id.imageView14);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            myRelativeLayout.addView(pieces[6][j].GetImageView(), layoutParams);
        }
        for (int i = 2; i <= 5; i++){
            for(int j = 0; j < 8; j++){
                pieces[i][j] = new Piece(i, j, pieces, "gray", null);
            }
        }
        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (pieces[i][j].HasPiece()){
                    createPiece(pieces[i][j], i, j);
                }
            }
        }
        RelativeLayout.LayoutParams layoutParams1;
        for (int i =0; i <= 1; i++){
            for (int j=0; j < 8; j++){
                layoutParams1 = (RelativeLayout.LayoutParams) pieces[i][j].GetImageView().getLayoutParams();
                layoutParams1.topMargin = width/6 + (width/8)*(i-1);  // distance between squares 101
                layoutParams1.rightMargin = (width/8)*(7-j); // distance between squares 101
                pieces[i][j].GetImageView().setClickable(false);
            }
        } // organizes the black pieces
        for (int i =6; i <= 7; i++){
            for (int j=0; j < 8; j++){
                layoutParams1 = (RelativeLayout.LayoutParams) pieces[i][j].GetImageView().getLayoutParams();
                layoutParams1.topMargin = width/6 + (width/8)*(i-1);  // distance between squares 101
                layoutParams1.rightMargin = (width/8)*(7-j); // distance between squares 101
                pieces[i][j].GetImageView().setClickable(false);
            }
        } // organizes the white pieces
        //System.out.println(width/16);
        //System.out.println(height);
        king = (King)FindKing("black");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isServiceBound && musicService != null && musicService.isMusicPlaying()) {
            musicService.pauseMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isServiceBound && musicService != null && !musicService.isMusicPlaying()) {
            musicService.resumeMusic();
        }
    }

    public Piece FindKing(String color){
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                if (pieces[i][j] instanceof King && pieces[i][j].GetColor() == color){
                    return pieces[i][j];
                }
            }
        }
        return null;
    }
    public void createPiece(Piece piece, int i, int j){ //Yoda: say the N word i must. Luke: NO Yoda you cant say that word. Yoda: yes i can you Ni.... - George lucas.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        android.view.ViewGroup.LayoutParams params = piece.GetImageView().getLayoutParams();
        params.width = width/8;
        params.height = width/8;
        piece.GetImageView().setLayoutParams(params);
    }
    public RelativeLayout.LayoutParams NewLayout(){
        return new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
    }
    public void ActualMove(Piece piece, int i, int j){
        King king1 = FindKingColor(piece.GetColor());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if (piece instanceof King){
            King king = (King)piece;
            if (king.CanCastleKingSide(pieces[i][j])){
                RelativeLayout.LayoutParams layoutParamsking = (RelativeLayout.LayoutParams) piece.GetImageView().getLayoutParams();
                RelativeLayout.LayoutParams layoutParamsrook = (RelativeLayout.LayoutParams) pieces[i][j].GetImageView().getLayoutParams();
                layoutParamsking.topMargin = width/6 + (width/8)*(king.GetI()-1);
                layoutParamsking.rightMargin = ((width/8));
                myRelativeLayout.removeView(piece.GetImageView());
                myRelativeLayout.addView(piece.GetImageView(), layoutParamsking);
                layoutParamsrook.topMargin = width/6 + (width/8)*(king.GetI()-1);
                layoutParamsrook.rightMargin = ((width/8) * 2);
                myRelativeLayout.removeView(pieces[i][j].GetImageView());
                myRelativeLayout.addView(pieces[i][j].GetImageView(), layoutParamsrook);
                king.MoveCastle(pieces[i][j]);
                DidCastle = true;
            }
            if (king.CanCastleQueenSide(pieces[i][j])){
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) piece.GetImageView().getLayoutParams();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) pieces[i][j].GetImageView().getLayoutParams();
                layoutParams1.topMargin = width/6 + (width/8)*(king.GetI()-1);
                layoutParams1.rightMargin = ((width/8) * (5));
                myRelativeLayout.removeView(piece.GetImageView());
                myRelativeLayout.addView(piece.GetImageView(), layoutParams1);
                layoutParams2.topMargin = width/6 + (width/8)*(king.GetI()-1);
                layoutParams2.rightMargin = ((width/8) * (4));
                myRelativeLayout.removeView(pieces[i][j].GetImageView());
                myRelativeLayout.addView(pieces[i][j].GetImageView(), layoutParams2);
                king.MoveCastle(pieces[i][j]);
                DidCastle = true;
            }
            turn++;
        }
        if (piece.CanMove(pieces[i][j], false) && !DidCastle){
            RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) piece.GetImageView().getLayoutParams();
            layoutParams1.topMargin = width/6 + (width/8)*(i-1);
            layoutParams1.rightMargin = ((width/8) * (7-j));

            if (piece instanceof Pawn){
                Pawn pawn = (Pawn)piece;
                if (pawn.IsEnPeasnt()){
                    RemoveEatenPiece(i - pawn.ReturnPawnInc(),j);
                }
            }
            //System.out.println(i + " " + j);
            RemoveEatenPiece(i,j);
            piece.Move(piece, i, j);
            IsPromotable(piece, pieces);
            if((king1.IsCheckMated())){
                Intent intent = new Intent(MainActivity.this, AndTheWinnerIs.class);
                Intent prevIntent = new Intent();
                intent.putExtra("username", prevIntent.getStringExtra("username"));
                if(prevIntent.getBooleanExtra("is_bot", false)){
                    intent.putExtra("is_bot", true);
                }
                else {
                    intent.putExtra("is_bot", false);
                }
                if (king1.GetColor() == "black"){
                    intent.putExtra("Winner", "white");

                    startActivity(intent);
                }

                else if (king1.GetColor() == "white"){
                    intent.putExtra("Winner", "black");
                    startActivity(intent);
                }}
            myRelativeLayout.removeView(piece.GetImageView());
            myRelativeLayout.addView(piece.GetImageView(), layoutParams1);
            turn++;
        }
        DidCastle = false;
        PrintBoardType();
    }
    public boolean onTouchEvent(MotionEvent event){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int x=0,y=0;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (event.getX() <= width && event.getX() > 0 && event.getY() > width/6 && event.getY() <= width/6  + width){
                x =  (int)event.getX() - (int)event.getX() % (width/8);
                y = ((int)event.getY() - (int)event.getY() % (width/8)) - (width/6);
                //y = (height - (int)event.getY()) - (int)event.getY() % 101- 150;
                System.out.println(y/(width/8) + "       " + x/(width/8));
                SaveY = y/(width/8);
                SaveX = x/(width/8);
                if (IsClicked){
                    //System.out.println(SavePiece.GetI() + "" + SavePiece.GetColor() + "" + SavePiece.GetJ() + "");
                    SaveI = SavePiece.GetI();
                    SaveJ = SavePiece.GetJ();
                    if (turn % 2 == 0){
                        if (pieces[SaveI][SaveJ].GetColor() == "white"){
                            if (!DidPromote){
                                ActualMove(pieces[SaveI][SaveJ], SaveY, SaveX);}
                        }
                    }
                    else{
                        if (pieces[SaveI][SaveJ].GetColor() == "black"){
                            if (!DidPromote){
                                ActualMove(pieces[SaveI][SaveJ], SaveY, SaveX);}
                        }
                    }
                    System.out.println("turn is : "+turn);
                    IsClicked = false; // white to play and win, and white wins this position by ....

                }
                else{
                    if (pieces[SaveY][SaveX].HasPiece())
                    {
                        SavePiece = pieces[SaveY][SaveX];
                        IsClicked = true;
                    }
                    else{
                        SavePiece = new Piece(420, 69, pieces,"gray", null);
                    }
                }
            }
            //System.out.println(IsClicked);
        }
        return true;
    }
    public void PrintBoardType(){
        for (int i = 0; i<8; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(pieces[i][j].GetType() + ", ");
            }
            System.out.println();
        }
        if (pieces[2][2].CanMove(pieces[5][2], true)){
            System.out.println("ayo");
        }
    }
    public void RemoveEatenPiece(int i, int j){
        myRelativeLayout.removeView(pieces[i][j].GetImageView());
    }
    public King FindKingColor(String color){
        if (color == "white"){
            return  (King)FindKing("black");
        }
        else{
            return (King)FindKing("white");
        }
    }
    public void IsPromotable(Piece piece, Piece[][] pieces){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if (piece instanceof Pawn)
        {
            if (piece.GetI() == 7)
            {
                DidPromote = true;
                PBQ.setVisibility(View.VISIBLE);
                PBQ.setClickable(true);
                PBB.setVisibility(View.VISIBLE);
                PBB.setClickable(true);
                PBR.setVisibility(View.VISIBLE);
                PBR.setClickable(true);
                PBKN.setVisibility(View.VISIBLE);
                PBKN.setClickable(true);
                PBQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(7, piece.GetJ());
                        pieces[7][piece.GetJ()] = new Queen(7,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        System.out.println(pieces[7][piece.GetJ()].GetType());
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[7][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PBQ.setVisibility(View.INVISIBLE);
                        PBQ.setClickable(false);
                        PBB.setVisibility(View.INVISIBLE);
                        PBB.setClickable(false);
                        PBR.setVisibility(View.INVISIBLE);
                        PBR.setClickable(false);
                        PBKN.setVisibility(View.INVISIBLE);
                        PBKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PBB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(7, piece.GetJ());
                        pieces[7][piece.GetJ()] = new Bishop(7,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[7][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PBQ.setVisibility(View.INVISIBLE);
                        PBQ.setClickable(false);
                        PBB.setVisibility(View.INVISIBLE);
                        PBB.setClickable(false);
                        PBR.setVisibility(View.INVISIBLE);
                        PBR.setClickable(false);
                        PBKN.setVisibility(View.INVISIBLE);
                        PBKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PBR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(7, piece.GetJ());
                        pieces[7][piece.GetJ()] = new Rook(7,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/16;
                        layoutParamsP.height = width/16;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[7][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PBQ.setVisibility(View.INVISIBLE);
                        PBQ.setClickable(false);
                        PBB.setVisibility(View.INVISIBLE);
                        PBB.setClickable(false);
                        PBR.setVisibility(View.INVISIBLE);
                        PBR.setClickable(false);
                        PBKN.setVisibility(View.INVISIBLE);
                        PBKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PBKN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(7, piece.GetJ());
                        pieces[7][piece.GetJ()] = new Knight(7,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[7][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PBQ.setVisibility(View.INVISIBLE);
                        PBQ.setClickable(false);
                        PBB.setVisibility(View.INVISIBLE);
                        PBB.setClickable(false);
                        PBR.setVisibility(View.INVISIBLE);
                        PBR.setClickable(false);
                        PBKN.setVisibility(View.INVISIBLE);
                        PBKN.setClickable(false);
                        DidPromote = false;
                    }
                });

            }
            if (piece.GetI() == 0)
            {
                DidPromote = true;
                PWQ.setVisibility(View.VISIBLE);
                PWQ.setClickable(true);
                PWB.setVisibility(View.VISIBLE);
                PWB.setClickable(true);
                PWR.setVisibility(View.VISIBLE);
                PWR.setClickable(true);
                PWKN.setVisibility(View.VISIBLE);
                PWKN.setClickable(true);
                PWQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(0, piece.GetJ());
                        pieces[0][piece.GetJ()] = new Queen(0,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        System.out.println(pieces[0][piece.GetJ()].GetType());
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[0][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PWQ.setVisibility(View.INVISIBLE);
                        PWQ.setClickable(false);
                        PWB.setVisibility(View.INVISIBLE);
                        PWB.setClickable(false);
                        PWR.setVisibility(View.INVISIBLE);
                        PWR.setClickable(false);
                        PWKN.setVisibility(View.INVISIBLE);
                        PWKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PWB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(0, piece.GetJ());
                        pieces[0][piece.GetJ()] = new Bishop(0,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[0][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PWQ.setVisibility(View.INVISIBLE);
                        PWQ.setClickable(false);
                        PWB.setVisibility(View.INVISIBLE);
                        PWB.setClickable(false);
                        PWR.setVisibility(View.INVISIBLE);
                        PWR.setClickable(false);
                        PWKN.setVisibility(View.INVISIBLE);
                        PWKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PWR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(0, piece.GetJ());
                        pieces[0][piece.GetJ()] = new Rook(0,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[0][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PWQ.setVisibility(View.INVISIBLE);
                        PWQ.setClickable(false);
                        PWB.setVisibility(View.INVISIBLE);
                        PWB.setClickable(false);
                        PWR.setVisibility(View.INVISIBLE);
                        PWR.setClickable(false);
                        PWKN.setVisibility(View.INVISIBLE);
                        PWKN.setClickable(false);
                        DidPromote = false;
                    }
                });
                PWKN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RelativeLayout.LayoutParams layoutParamsP = NewLayout();
                        RemoveEatenPiece(0, piece.GetJ());
                        pieces[0][piece.GetJ()] = new Knight(0,piece.GetJ(),pieces, piece.GetColor(),  new ImageView(MainActivity.this));
                        layoutParamsP = NewLayout();
                        layoutParamsP.width = width/8;
                        layoutParamsP.height = width/8;
                        layoutParamsP.addRule(RelativeLayout.BELOW, R.id.imageView14);
                        layoutParamsP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        myRelativeLayout.addView(pieces[0][piece.GetJ()].GetImageView(), layoutParamsP);
                        layoutParamsP.topMargin = width/6 + (width/8)*(piece.GetI()-1);
                        layoutParamsP.rightMargin = (width/8)*(7- piece.GetJ());
                        PWQ.setVisibility(View.INVISIBLE);
                        PWQ.setClickable(false);
                        PWB.setVisibility(View.INVISIBLE);
                        PWB.setClickable(false);
                        PWR.setVisibility(View.INVISIBLE);
                        PWR.setClickable(false);
                        PWKN.setVisibility(View.INVISIBLE);
                        PWKN.setClickable(false);
                        DidPromote = false;
                    }
                });
            }
        }
    }
}