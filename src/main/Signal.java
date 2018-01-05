package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Signal implements Comparator<Signal> {

	private boolean green;
	private boolean red;
	private boolean upperYellow;
	private boolean lowerYellow;
	private boolean white;
	private String nextSpeed = "";
	private String speedLimit = "";
	private String loadId;

	private List<Track> tracklist = new ArrayList<>();

	Signal(String loadId) {
		this.loadId = loadId;
	}

	boolean isGreenLightOn() {
		return green;
	}

	private void setGreenLightOn(boolean green) {
		this.green = green;
	}

	boolean isRedLightOn() {
		return red;
	}

	private void setRedLightOn(boolean red) {
		this.red = red;
	}

	boolean isUpperYellowLightOn() {
		return upperYellow;
	}

	private void setUpperYellowLightOn(boolean upperYellow) {
		this.upperYellow = upperYellow;
	}

	boolean isLowerYellowLightOn() {
		return lowerYellow;
	}

	private void setLowerYellowLightOn(boolean lowerYellow) {
		this.lowerYellow = lowerYellow;
	}

	boolean isWhiteLightOn() {
		return white;
	}

	private void setWhiteLightOn(boolean white) {
		this.white = white;
	}

	String getNextSpeed() {
		return nextSpeed;
	}

	void setNextSpeed(String nextSpeed) {
		this.nextSpeed = nextSpeed;
	}

	String getSpeedLimit() {
		return speedLimit;
	}

	void setSpeedLimit(String speedLimit) {
		this.speedLimit = speedLimit;
	}

	String getLoadId() {
		return loadId;
	}

	void setLoadId(String loadId) {
		this.loadId = loadId;
	}

	void setSignalGreen() {
		setGreenLightOn(true);
		setRedLightOn(false);
		setUpperYellowLightOn(false);
		setLowerYellowLightOn(false);
		setWhiteLightOn(false);
	}

	void setSignalRed() {
		setGreenLightOn(false);
		setRedLightOn(true);
		setUpperYellowLightOn(false);
		setLowerYellowLightOn(false);
		setWhiteLightOn(false);
	}

	void setSignalDoubleYellow() {
		setGreenLightOn(false);
		setRedLightOn(false);
		setUpperYellowLightOn(true);
		setLowerYellowLightOn(true);
		setWhiteLightOn(false);
	}

	void setSignalGreenYellowHorizontal() {
		setGreenLightOn(true);
		setRedLightOn(false);
		setUpperYellowLightOn(true);
		setLowerYellowLightOn(false);
		setWhiteLightOn(false);
	}

	void setSignalGreenYellowVertical() {
		setGreenLightOn(true);
		setRedLightOn(false);
		setUpperYellowLightOn(false);
		setLowerYellowLightOn(true);
		setWhiteLightOn(false);
	}

	void setSignalRedWhite() {
		setGreenLightOn(false);
		setRedLightOn(true);
		setUpperYellowLightOn(false);
		setLowerYellowLightOn(false);
		setWhiteLightOn(true);
	}

	boolean isSignalGreen() {
		if (isGreenLightOn() && !isRedLightOn() && !isUpperYellowLightOn() && !isLowerYellowLightOn()
				&& !isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	boolean isSignalRed() {
		if (!isGreenLightOn() && isRedLightOn() && !isUpperYellowLightOn() && !isLowerYellowLightOn()
				&& !isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	boolean isSignalDoubleYellow() {
		if (!isGreenLightOn() && !isRedLightOn() && isUpperYellowLightOn() && isLowerYellowLightOn()
				&& !isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	boolean isSignalGreenYellowHorizontal() {
		if (isGreenLightOn() && !isRedLightOn() && isUpperYellowLightOn() && !isLowerYellowLightOn()
				&& !isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	boolean isSignalGreenYellowVertical() {
		if (isGreenLightOn() && !isRedLightOn() && !isUpperYellowLightOn() && isLowerYellowLightOn()
				&& !isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	boolean isSignalRedWhite() {
		if (!isGreenLightOn() && isRedLightOn() && !isUpperYellowLightOn() && !isLowerYellowLightOn()
				&& isWhiteLightOn()) {
			return true;
		} else
			return false;
	}

	void calculatePosition() {
		speedLimit = "";
		nextSpeed = "";
		Track track = getActiveTrack();
		Signal nextSignal = track.getSignal();

		// mainline section

		if (track.isOccupied()) {
			this.setSignalRed();
		} else if (track.getType().equals("siding")) {
			this.setSignalRedWhite();
		} else if (nextSignal == null || nextSignal.isSignalRed() || nextSignal.isSignalRedWhite()) {
			this.setSignalDoubleYellow();
		} else if (nextSignal.isSignalDoubleYellow() && nextSignal.getActiveTrack().getLength() <= 350) {
			this.setSignalGreenYellowVertical();
		} else {
			this.setSignalGreen();
		}

		// speed section

		if (nextSignal != null) {
			if (!track.isOccupied() && !track.getSpeedLimit().equals("")) {
				this.setSpeedLimit(track.getSpeedLimit());
			} else if (!track.isOccupied() && !nextSignal.getSpeedLimit().equals("")) {
				this.setNextSpeed(nextSignal.getSpeedLimit());
				this.setSignalGreenYellowHorizontal();
			}
		}

		// siding section

		if (nextSignal != null && !track.isOccupied() && track.type.equals("siding")
				&& nextSignal.getActiveTrack().type.equals("mainline")) {
			this.setSignalDoubleYellow();
		}
	}

	List<Track> getTrackList() {
		return tracklist;
	}

	void addTrack(int length, String type, String speedLimit, boolean direction) {
		Track track = new Track(length, type, speedLimit, direction);
		tracklist.add(track);
	}
	
	void attTrack(Track track) {
		
	}

	void setTrackActive(int index) {
		for (Track track : tracklist) {
			track.active = false;
		}
		tracklist.get(index).active = true;
		SignalManager.updateAllSignals();
	}

	Track getActiveTrack() {
		Track track = null;
		for (Track listTrack : tracklist) {
			if (listTrack.isActive()) {
				track = listTrack;
				break;
			}
		}
		return track;
	}

	int getActiveTrackIndex() {
		int x = -1;
		for (int i = 0; i < tracklist.size(); i++) {
			if (tracklist.get(i).isActive()) {
				x = i;
				break;
			}
		}
		return x;
	}

	class Track {

		private boolean occupied;
		private int length;
		private Signal signal;
		private boolean active;
		private String type;
		private String speedLimit;
		private boolean direction;

		Track(int length, String type, String speedLimit, boolean direction) {
			this.length = length;
			this.type = type;
			this.speedLimit = speedLimit;
			this.direction = direction;
		}

		String getType() {
			return type;
		}

		void setType(String type) {
			this.type = type;
		}

		boolean isActive() {
			return active;
		}

		boolean isOccupied() {
			return occupied;
		}

		void setOccupied(boolean occupied) {
			this.occupied = occupied;
			SignalManager.updateAllSignals();
		}

		int getLength() {
			return length;
		}

		void setLength(int length) {
			this.length = length;
		}

		Signal getSignal() {
			return signal;
		}

		void setSignal(Signal signal) {
			this.signal = signal;
		}

		String getSpeedLimit() {
			return speedLimit;
		}

		void setSpeedLimit(String speedLimit) {
			this.speedLimit = speedLimit;
		}

		public boolean isDirection() {
			return direction;
		}

		public void setDirection(boolean direction) {
			this.direction = direction;
		}

	}

	@Override
	public int compare(Signal s1, Signal s2) {
		if (s1.getLoadId().compareToIgnoreCase(s2.getLoadId()) != 0) {
			return s1.getLoadId().compareToIgnoreCase(s2.getLoadId());
		} else
			return 0;
	}

}
