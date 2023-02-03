package aqua.logic.command;

import java.util.List;

import aqua.aquatask.AquaTask;
import aqua.logic.ArgumentMap;
import aqua.logic.ExecutionDisplayerTask;
import aqua.logic.ExecutionService;
import aqua.logic.ExecutionTask;
import aqua.manager.IoManager;
import aqua.manager.LogicManager;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

public class ViewScheduleCommand extends CommandController {
    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager manager) {
        return ExecutionService.of(new ExecutionTask<List<AquaTask>>(args, manager) {
            @Override
            protected List<AquaTask> process(ArgumentMap args, LogicManager manager) {
                return filterTasks(args, manager);
            }
        });
    }


    @Override
    public ExecutionService getService(ArgumentMap args, LogicManager logicManager, IoManager ioManager) {
        return ExecutionService.of(new ExecutionDisplayerTask<List<AquaTask>>(args, logicManager, ioManager) {
            @Override
            protected List<AquaTask> process(ArgumentMap args, LogicManager manager) {
                return filterTasks(args, manager);
            }

            @Override
            protected void display(List<AquaTask> tasks, IoManager manager) {
                manager.popup(new SchedulePopup());
            }
        });
    }


    private List<AquaTask> filterTasks(ArgumentMap args, LogicManager manager) {
        return null;
    }





    private class SchedulePopup extends Popup {
        private boolean isDragged = false;
        private double x = 0;
        private double y = 0;


        SchedulePopup() {
            VBox box = new VBox();
            box.getChildren().add(new Circle(50));
            getContent().add(box);
            setWidth(100);
            setHeight(100);
            box.setOnMousePressed(this::handleMousePress);
            box.setOnMouseDragged(this::handleMouseDragged);
            box.setOnMouseReleased(this::handleMouseRelease);
        }


        private void handleMousePress(MouseEvent event) {
            if (!isDragged) {
                x = event.getScreenX();
                y = event.getScreenY();
            }
            isDragged = true;
        }

        private void handleMouseRelease(MouseEvent event) {
            isDragged = false;
        }

        private void handleMouseDragged(MouseEvent event) {
            double xChange = event.getScreenX() - x;
            double yChange = event.getScreenY() - y;
            setX(getX() + xChange);
            setY(getY() + yChange);
            x = event.getScreenX();
            y = event.getScreenY();
        }
    }
}
