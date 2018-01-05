package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.Signal.Track;

public class SignalApp {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		Signal signal5 = new Signal("5");
		Signal signal4 = new Signal("4");
		Signal signal3 = new Signal("3");
		Signal signal2 = new Signal("2");
		Signal signal1 = new Signal("1");
		Signal signal4a = new Signal("4a");
		Signal signal6 = new Signal("6");
		Signal signal5a = new Signal("5a");
		signal1.addTrack(1500, "mainline", "", true);
		signal2.addTrack(600, "mainline", "", true);
		signal3.addTrack(300, "mainline", "", true);
		signal3.addTrack(1000, "mainline", "6", false);
		signal4.addTrack(1000, "mainline", "", true);
		signal5.addTrack(1000, "mainline", "", true);
		signal4a.addTrack(600, "mainline", "", true);
		signal6.addTrack(1800, "siding", "", true);
		signal5a.addTrack(450, "mainline", "", true);
		SignalManager.setNextSignal(signal1, 0, signal2);
		SignalManager.setNextSignal(signal2, 0, signal3);
		SignalManager.setNextSignal(signal3, 0, signal4);
		SignalManager.setNextSignal(signal4, 0, signal5);
		SignalManager.setNextSignal(signal3, 1, signal4a);
		SignalManager.setNextSignal(signal5, 0, signal6);
		SignalManager.setNextSignal(signal4a, 0, signal5a);
		SignalManager.setTrackActive(signal5, 0);
		SignalManager.setTrackActive(signal4, 0);
		SignalManager.setTrackActive(signal4a, 0);
		SignalManager.setTrackActive(signal3, 1);
		SignalManager.setTrackActive(signal2, 0);
		SignalManager.setTrackActive(signal1, 0);
		SignalManager.setTrackActive(signal6, 0);
		SignalManager.setTrackActive(signal5a, 0);

		SignalManager.addSignal(signal1);
		SignalManager.addSignal(signal2);
		SignalManager.addSignal(signal3);
		SignalManager.addSignal(signal4);
		SignalManager.addSignal(signal5);
		SignalManager.addSignal(signal4a);
		SignalManager.addSignal(signal6);
		SignalManager.addSignal(signal5a);
		SignalManager.sortSignalList();

		while (true) {
			SignalManager.displayAllSignalsPerAspect();

			for (Signal signal : SignalManager.getSignalList()) {
				SignalManager.setTrackFree(signal);
			}

			System.out.println("Which track do you want to occupy?");
			System.out.println("8. Track 5a");
			System.out.println("7. Track 4a");
			System.out.println("6. Track 6");
			System.out.println("5. Track 5");
			System.out.println("4. Track 4");
			System.out.println("3. Track 3");
			System.out.println("2. Track 2");
			System.out.println("1. Track 1");

			int a = enterValue();
			switch (a) {
			case 1:
				setTrackOccupied(signal1);
				break;
			case 2:
				setTrackOccupied(signal2);
				break;
			case 3:
				setTrackOccupied(signal3);
				break;
			case 4:
				setTrackOccupied(signal4);
				break;
			case 5:
				setTrackOccupied(signal5);
				break;
			case 6:
				setTrackOccupied(signal6);
				break;
			case 7:
				setTrackOccupied(signal4a);
				break;
			case 8:
				setTrackOccupied(signal5a);
			}
		}
	}

	private static void setTrackOccupied(Signal signal) {
		Track track = null;
		for (Track listTrack : signal.getTrackList()) {
			if (listTrack.isActive()) {
				track = listTrack;
				break;
			}
		}
		SignalManager.setTrackOccupied(track);
	}

	private static int enterValue() {
		int a = 0;
		try {
			a = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.exit(0);
		}
		return a;
	}

}
