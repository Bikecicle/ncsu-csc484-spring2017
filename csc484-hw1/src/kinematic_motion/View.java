package kinematic_motion;

import processing.core.PApplet;

public class View extends PApplet{
	
	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int characterRadius = 10;

    public static void main(String[] args) {
        PApplet.main("kinematic_motion.View");
    }

    public void settings(){
        size(viewWidth,viewHeight);
    }

    public void setup(){
        fill(120,50,240);
    }

    public void draw(){
        
    }
    
    private void renderCharacter( Character c ) {
    	
    }
	
}
