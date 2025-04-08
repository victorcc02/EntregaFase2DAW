package es.codeurjc.daw.alphagym.dtosedit;

public class Intensity {
    
    private String level;
    private boolean selected;

    public Intensity() {
    }

    public Intensity(String level, boolean selected) {
        this.level = level;
        this.selected = selected;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
