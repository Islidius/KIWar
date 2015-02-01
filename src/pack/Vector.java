package pack;

public class Vector {
	
	private double[] xyzw;
	
	
	//Constructor -----------------------------------------------------------------------------------------
	public Vector(double x,double y){
		xyzw = new double[3];
		xyzw[0] = x;xyzw[1] = y;xyzw[2] = 1;
	}
	
	public Vector(double x,double y,double z){
		xyzw = new double[4];
		xyzw[0] = x;xyzw[1] = y;xyzw[2] = z;xyzw[3] = 1;
	}
	
	public Vector(Vector v){
		xyzw = new double[v.size()];
		for(int i = 0;i<v.size();i++){
			xyzw[i] = v.get()[i];
		}
	}
	
	//normal functions -------------------------------------------------------------------------------------
	public Vector add(Vector v){
		if(size() == 3 && v.size() == 3){ // 2D
			return new Vector(getX()+v.getX(),getY()+v.getY());
		}
		else if(size() == 4 && v.size() == 4){ // 3D
			return new Vector(getX()+v.getX(),getY()+v.getY(),getZ()+v.getZ());
		}
		return null; // nicht gleich
	}
	
	public Vector multiply(double factor){
		if(size() == 3)return new Vector(getX()*factor,getY()*factor);  // 2D
		if(size() == 4)return new Vector(getX()*factor,getY()*factor,getZ()*factor); // 3D
		return null;
	}
	
	public Vector multiply(Matrix m){
		if(size() == 3 && m.matrix.length == 9){ // 2D
			double[] sh = new double[3];
			sh[0] = 0;sh[1] = 0;sh[2] = 0;
			
			for(int i = 0;i<9;i++){
				sh[i/3] += m.matrix[i]*xyzw[i%3];
			}
			return new Vector(sh[0],sh[1]);
		}
		else if (size() == 4 && m.matrix.length == 16){ // 3D
			double[] sh = new double[4];
			sh[0] = 0;sh[1] = 0;sh[2] = 0;sh[3] = 0;
			
			for(int i = 0;i<16;i++){
				sh[i/4] += m.matrix[i]*xyzw[i%4];
			}
			return new Vector(sh[0],sh[1],sh[2]);
		}
		return null; // nicht compatibel
	}
	
	public double scalar(Vector v){
		if(size() == 3 && v.size() == 3){ // 2D
			return getX()*v.getX()+getY()*v.getY();
		}
		else if(size() == 4 && v.size() == 4){ // 3D
			return getX()*v.getX()+getY()*v.getY()+getZ()*v.getZ();
		}
		return Double.NaN;
	}
	
	public double length(){
		return Math.sqrt(scalar(this));
	}
	
	public Vector normalize(){
		return multiply(1.0D/ (length()!= 0 ? length() : 1));
	}
	
	public double distance(Vector v){
		Vector delta = add(v.multiply(-1)); // Delta Vector this+-v
		return delta.length(); // lenght;
	}
	
	public double getyrot(){
		if(xyzw.length == 4)return Math.atan2(getX(), getZ());
		else return Math.atan2(getX(), getY());
	}
	
	//print Methods ----------------------------------------------------------------------------------------
	public void print(){
		if(size() == 3)System.out.println("Vector(2D) : ("+getX()+"/"+getY()+")");
		if(size() == 4)System.out.println("Vector(3D) : ("+getX()+"/"+getY()+"/"+getZ()+")");
	}
	
	//get Methods ------------------------------------------------------------------------------------------
	public int size(){
		return xyzw.length;
	}
	public double[] get(){
		return xyzw;
	}
	public double getX(){
		return xyzw[0];
	}
	public double getY(){
		return xyzw[1];
	}
	public double getZ(){
		return xyzw.length == 4 ? xyzw[2] : null; // nicht 3D
	}

}
