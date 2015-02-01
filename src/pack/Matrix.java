package pack;

public class Matrix {
	
	public static int SCALE = 1;
	public static int TRANS = 0;
	public static int ROT2D = 2;
	public static int ROT3D_X = 3;
	public static int ROT3D_Y = 4;
	public static int ROT3D_Z = 5;
	
	double[] matrix;
	
	public Matrix(int mode,double x,double y){
		if(mode == 0){
			matrix = new double[9];
			matrix[0] = 1;matrix[1] = 0;matrix[2] = x;
			matrix[3] = 0;matrix[4] = 1;matrix[5] = y;
			matrix[6] = 0;matrix[7] = 0;matrix[8] = 1;
		}
		else if(mode == 1){
			matrix = new double[9];
			matrix[0] = x;matrix[1] = 0;matrix[2] = 0;
			matrix[3] = 0;matrix[4] = y;matrix[5] = 0;
			matrix[6] = 0;matrix[7] = 0;matrix[8] = 1;
		}
	}
	
	public Matrix(int mode,double rangle){ // rangle in radian
		if(mode == 2){
			matrix = new double[9];
			matrix[0] = Math.cos(rangle);matrix[1] = -Math.sin(rangle);matrix[2] = 0;
			matrix[3] = Math.sin(rangle);matrix[4] = Math.cos(rangle);matrix[5] = 0;
			matrix[6] = 0;matrix[7] = 0;matrix[8] = 1;
		}
		else if(mode == 3){
			matrix = new double[16];
			matrix[0] = 1;matrix[1] = 0;matrix[2] = 0;matrix[3] = 0;
			matrix[4] = 0;matrix[5] = Math.cos(rangle);matrix[6] = -Math.sin(rangle);matrix[7] = 0;
			matrix[8] = 0;matrix[9] = Math.sin(rangle);matrix[10] = Math.cos(rangle);matrix[11] = 0;
			matrix[12] = 0;matrix[13] = 0;matrix[14] = 0;matrix[15] = 1;
		}
		else if(mode == 4){
			matrix = new double[16];
			matrix[0] = Math.cos(rangle);matrix[1] = 0;matrix[2] = Math.sin(rangle);matrix[3] = 0;
			matrix[4] = 0;matrix[5] = 1;matrix[6] = 0;matrix[7] = 0;
			matrix[8] = -Math.sin(rangle);matrix[9] = 0;matrix[10] = Math.cos(rangle);matrix[11] = 0;
			matrix[12] = 0;matrix[13] = 0;matrix[14] = 0;matrix[15] = 1;
		}
		else if(mode == 5){
			matrix = new double[16];
			matrix[0] = Math.cos(rangle);matrix[1] =- Math.sin(rangle);matrix[2] = 0;matrix[3] = 0;
			matrix[4] = Math.sin(rangle);matrix[5] = Math.cos(rangle);matrix[6] = 0;matrix[7] = 0;
			matrix[8] = 0;matrix[9] = 0;matrix[10] = 1;matrix[11] = 0;
			matrix[12] = 0;matrix[13] = 0;matrix[14] = 0;matrix[15] = 1;
		}
	}
	
	public Matrix(int mode,double x,double y,double z){
		if(mode == 0){
			matrix = new double[16];
			matrix[0] = 1;matrix[1] = 0;matrix[2] = 0;matrix[3] = x;
			matrix[4] = 0;matrix[5] = 1;matrix[6] = 0;matrix[7] = y;
			matrix[8] = 0;matrix[9] = 0;matrix[10] = 1;matrix[11] = z;
			matrix[12] = 0;matrix[13] = 0;matrix[14] = 0;matrix[15] = 1;
		}
	}

}
