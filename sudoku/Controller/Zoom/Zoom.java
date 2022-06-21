package sudoku.Controller.Zoom;

import sudoku.View.View;

public class Zoom {

    private View view;

    private int zoomStatus = 28;
    private int zoomIncrement = 5; // Amount of zoom.

    private ZoomObjectInterface[][] zoomObjectArray;
	/*
	 * Author: Frederik
	 * Function: Create zoom object
	 * Input: Objects that can zoom and View 
	 */
    public Zoom(ZoomObjectInterface[][] zoomObjectArray, View view) {
        this.zoomObjectArray = zoomObjectArray;
        this.view = view;
        zoom();
    }
	/*
	 * Author: Frederik
	 * Function: Zooms in
	 */
    public void zoomIn() {
        this.zoomStatus = this.zoomStatus + zoomIncrement;
        zoom();
    }
	/*
	 * Author: Frederik
	 * Function: Zooms out
	 */
    public void zoomOut() {
        this.zoomStatus = this.zoomStatus - zoomIncrement;
        zoom();
    }
	/*
	 * Author: Frederik
	 * Function: Runs through all objects that zooms. Zooming either in or out
	 */
    private void zoom() {
        for (ZoomObjectInterface[] array : zoomObjectArray) {
            for (ZoomObjectInterface object : array) {
                object.setSize(zoomStatus);
            }
        }
        view.pack();
        view.centerOnScreen(view.getX() + (view.getWidth() / 2), view.getY() + (view.getHeight() / 2));
    }
}
