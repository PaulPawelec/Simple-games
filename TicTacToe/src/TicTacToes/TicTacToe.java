/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToes;

import javax.swing.JOptionPane;

/**
 *
 * @author Paweł
 */
public class TicTacToe extends javax.swing.JFrame {

    public static int player = 1, opponent = -1;
    public int turn = 0;
    public int buttons[][] = {{0, 0, 0},
                              {0, 0, 0},
                              {0, 0, 0}};
    
    public int evaluate(int[][] gameBoard){
		//Check rows
		for (int row = 0; row < 3; row++) {
			if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][1] == gameBoard[row][2]) {
				if (gameBoard[row][0] == player)
					return 10;
				if (gameBoard[row][0] == opponent)
					return -10;
			}
		}
		
		//Check columns
		for (int col = 0; col < 3; col++) {
			if (gameBoard[0][col] == gameBoard[1][col] && gameBoard[1][col] == gameBoard[2][col]) {
				if (gameBoard[0][col] == player)
					return 10;
				if (gameBoard[0][col] == opponent)
					return -10;
			}
		}
		
		
		//Check diagonal
		if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2]) {
			if (gameBoard[0][0] == player)
				return 10;
			if (gameBoard[0][0] == opponent)
				return -10;
		}
		if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0]) {
			if (gameBoard[0][2] == player)
				return 10;
			if (gameBoard[0][2] == opponent)
				return -10;
		}
		
		return 0;
	}
    public boolean isMoveLeft(int[][] gameBoard) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (gameBoard[i][j] == 0)
					return true;
			}
		}
		return false;
	}
    public int miniMax(int[][] gameBoard, boolean isMax, int depth, int alpha, int beta) {
		
		//Assign the Win Value to score
		int score = evaluate(gameBoard);
		
		//If it's the maximizer's score
		if (score == 10)
			return score;
		
		if (score == -10)
			return score;
		
		if (isMoveLeft(gameBoard) == true)
			return 0;
		
		
		if(isMax) {
			//If maximizer, best = -1000, if minimizer, best = 1000
			int best = -1000;
			
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col <3; col++) {
					if (gameBoard[row][col] == 0) {
						//Pick the firstmost empty cell
						gameBoard[row][col] = player;
						int val = miniMax(gameBoard, !isMax, depth + 1, alpha, beta);
                                                best = Math.max(best, val);
                                                alpha = Math.max(best, alpha);
						//Choose the maximum value
						//Undo the move
						gameBoard[row][col] = 0;
                                                if(beta <= alpha)
                                                    break;
					}
                                        if(beta <= alpha)
                                            break;
				}
			}
			
			return best;
		}
		//Minimizer's move
		else {
			int best = 1000;
			
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col <3; col++) {
					if (gameBoard[row][col] == 0) {
						//Pick the firstmost empty cell
						gameBoard[row][col] = opponent;
						int val = miniMax(gameBoard, !isMax, depth + 1, alpha, beta);
                                                best = Math.min(best, val);
                                                beta = Math.min(best, alpha);
						//Choose the maximum value
						//Undo the move
						gameBoard[row][col] = 0;
                                                if(beta <= alpha)
                                                    break;
					}
                                        if(beta <= alpha)
                                            break;
				}
			}
			
			return best;
		}
	}
    int rooow, coool;
    public int findBestMove(int[][] gameBoard) {		
		int bestVal = -1000;
		int alpha = -1000;
                int beta = 1000;
                
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (gameBoard[i][j] == 0) {
					
					//Make the move
					gameBoard[i][j] = player;
					
					//Compute the WinLoseStatus for this move
					int moveVal = miniMax(gameBoard, false, 0, alpha, beta);
					
					//Undo the move
					gameBoard[i][j] = 0;
					
					//Check if the value of moveVal is > bestVal or not, if it is, update bestVal
					if (moveVal > bestVal) {
						rooow = i;
						coool = j;
						bestVal = moveVal;
					}
				}
			}
		}
		
		return 0;
	}
    void CPU(){
        findBestMove(buttons);
        buttons[rooow][coool] = -1;
        if(rooow == 0 && coool == 0){
            jButton1.setText("O");
        }
        else if(rooow == 0 && coool == 1){
            jButton2.setText("O");
        }
        else if(rooow == 0 && coool == 2){
            jButton3.setText("O");
        }
        else if(rooow == 1 && coool == 0){
            jButton4.setText("O");
        }
        else if(rooow == 1 && coool == 1){
            jButton5.setText("O");
        }
        else if(rooow == 1 && coool == 2){
            jButton6.setText("O");
        }
        else if(rooow == 2 && coool == 0){
            jButton7.setText("O");
        }
        else if(rooow == 2 && coool == 1){
            jButton8.setText("O");
        }
        else if(rooow == 2 && coool == 2){
            jButton9.setText("O");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != -10){
            turn++;
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == -10){
            JOptionPane.showMessageDialog(rootPane, "YOU LOSE");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }
    
    
    public TicTacToe() {
        initComponents();
        setSize(400, 400);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Vijaya", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TIC TAC TOE");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.GridLayout(3, 3));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton7.setContentAreaFilled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton8.setContentAreaFilled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("MV Boli", 0, 64)); // NOI18N
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton9, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel10);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(buttons[0][0] == 0){
            if(turn%2 == 0){
                turn++;
                jButton1.setText("X");
                buttons[0][0] = 1;
            }
            else{
                turn++;
                jButton1.setText("O");
                buttons[0][0] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(buttons[0][1] == 0){
            if(turn%2 == 0){
                turn++;
                jButton2.setText("X");
                buttons[0][1] = 1;
            }
            else{
                turn++;
                jButton2.setText("O");
                buttons[0][1] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(buttons[0][2] == 0){
            if(turn%2 == 0){
                turn++;
                jButton3.setText("X");
                buttons[0][2] = 1;
            }
            else{
                turn++;
                jButton3.setText("O");
                buttons[0][2] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(buttons[1][0] == 0){
            if(turn%2 == 0){
                turn++;
                jButton4.setText("X");
                buttons[1][0] = 1;
            }
            else{
                turn++;
                jButton4.setText("O");
                buttons[1][0] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(buttons[1][1] == 0){
            if(turn%2 == 0){
                turn++;
                jButton5.setText("X");
                buttons[1][1] = 1;
            }
            else{
                turn++;
                jButton5.setText("O");
                buttons[1][1] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(buttons[1][2] == 0){
            if(turn%2 == 0){
                turn++;
                jButton6.setText("X");
                buttons[1][2] = 1;
            }
            else{
                turn++;
                jButton6.setText("O");
                buttons[1][2] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(buttons[2][0] == 0){
            if(turn%2 == 0){
                turn++;
                jButton7.setText("X");
                buttons[2][0] = 1;
            }
            else{
                turn++;
                jButton7.setText("O");
                buttons[2][0] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(buttons[2][1] == 0){
            if(turn%2 == 0){
                turn++;
                jButton8.setText("X");
                buttons[2][1] = 1;
            }
            else{
                turn++;
                jButton8.setText("O");
                buttons[2][1] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(buttons[2][2] == 0){
            if(turn%2 == 0){
                turn++;
                jButton9.setText("X");
                buttons[2][2] = 1;
            }
            else{
                turn++;
                jButton9.setText("O");
                buttons[2][2] = -1;
            }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "BYLO");
        }
        if(isMoveLeft(buttons) == true && evaluate(buttons) != 10){
            CPU();
        }
        else if(isMoveLeft(buttons) == true || evaluate(buttons) == 10){
            JOptionPane.showMessageDialog(rootPane, "YOU WIN");
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "DRAW");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
