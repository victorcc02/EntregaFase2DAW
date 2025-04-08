package es.codeurjc.daw.alphagym.dtosedit;

public class Goal {
    private String objective;
    private boolean selected;

    public Goal() {
    }

    public Goal(String objective, boolean selected) {
        this.objective = objective;
        this.selected = selected;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
