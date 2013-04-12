package de.azapps.mirakel;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener extends Activity implements View.OnTouchListener {
	private static final String TAG = "SwipeListener";
	private float start_x = 0, start_y = 0;
	private boolean myreturn = false;
	private Map<Direction, SwipeCommand> commands;
	private Direction direction;

	public SwipeListener(boolean myreturn, Map<Direction, SwipeCommand> commands) {
		this.myreturn = myreturn;
		if (commands == null)
			this.commands = new HashMap<SwipeListener.Direction, SwipeCommand>();
		else
			this.commands = commands;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		SwipeCommand c = null;
		if (action == MotionEvent.ACTION_DOWN) {
			start_x = event.getRawX();
			start_y = event.getRawY();
		} else if (action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_CANCEL) {
			float dx = start_x - event.getRawX();
			float dy = start_y - event.getRawY();
			if (dy > 3 * dx && dy > v.getHeight() / 3) {
				Log.v(TAG, "swipe up");
				direction = Direction.UP;
				c = commands.get(Direction.UP);
			} else if (dx > 3 * dy && dx > v.getWidth() / 3) {
				Log.v(TAG, "swipe rigth");
				direction = Direction.RIGHT;
				c = commands.get(Direction.RIGHT);
			} else if (dy < -3 * dx && -1 * dy > v.getHeight() / 3) {
				Log.v(TAG, "swipe down");
				direction = Direction.DOWN;
				c = commands.get(Direction.DOWN);
			} else if (dx < -3 * dy && -1 * dx > v.getWidth() / 3) {
				Log.v(TAG, "swipe left");
				direction = Direction.LEFT;
				c = commands.get(Direction.LEFT);
			} else {
				direction = Direction.NONE;
				Log.v(TAG, "Nothing");
			}
		}
		if (c != null) {
			c.runCommand(v, event);
		}

		return myreturn;
	}

	public Direction getDirection() {
		return direction;
	}

	public static enum Direction {
		UP, DOWN, RIGHT, LEFT, NONE
	}

}