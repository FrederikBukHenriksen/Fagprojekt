package sudoku.Controller.Zoom;

import sudoku.View.View;

public class Zoom {

    private View view;

    private int zoomStatus = 28;
    private int zoomIncrement = 5; // Amount of zoom.

    private ZoomObjectInterface[][] zoomObjectArray;

    public Zoom(ZoomObjectInterface[][] zoomObjectArray, View view) {
        this.zoomObjectArray = zoomObjectArray;
        this.view = view;
        zoom();
    }

    public void zoomIn() {
        this.zoomStatus = this.zoomStatus + zoomIncrement;
        zoom();
    }

    public void zoomOut() {
        this.zoomStatus = this.zoomStatus - zoomIncrement;
        zoom();
    }

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
