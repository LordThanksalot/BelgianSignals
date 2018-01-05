package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class SignalManager {

	private static Scanner scanner = new Scanner(System.in);
	private static List<Signal> signals = new ArrayList<>();

	private SignalManager() {
	}

	static List<Signal> getSignalList() {
		return signals;
	}

	static void addSignal(Signal signal) {
		signals.add(signal);
	}

	static void updateAllSignals() {
		for (Signal signal : signals) {
			signal.calculatePosition();
		}
	}

	static void sortSignalList() {
		signals.sort(new Signal("") {
		});
		Collections.reverse(signals);

		int x = 0;
		Signal formerSignal = new Signal("");

		for (int i = 0; i < signals.size(); i++) {
			Signal signal = signals.get(i);

			if (formerSignal.getLoadId().startsWith(signal.getLoadId().substring(0, 1))) {
				Collections.swap(signals, i, x);
			}
			formerSignal = signal;
			x = i;
		}
	}

	static void setSignalPositionManually(Signal signal) {

		System.out.println("Geef de seinstand aan om weer te geven (getal):");
		System.out.println("1. Groen");
		System.out.println("2. Rood");
		System.out.println("3. Dubbel geel");
		System.out.println("4. Groen-geel horizontaal");
		System.out.println("5. Groen-geel verticaal");
		System.out.println("6. Rood-wit");
		int x = enterInteger();

		switch (x) {
		case (1):
			signal.setSignalGreen();
			break;
		case (2):
			signal.setSignalRed();
			break;
		case (3):
			signal.setSignalDoubleYellow();
			break;
		case (4):
			signal.setSignalGreenYellowHorizontal();
			break;
		case (5):
			signal.setSignalGreenYellowVertical();
			break;
		case (6):
			signal.setSignalRedWhite();
			break;
		default:
			System.out.println("You really screwed up this time!");
		}
	}

	private static int enterInteger() {
		boolean error = true;
		int integer = 0;
		while (error) {
			try {
				integer = scanner.nextInt();
				if (integer < 1 || integer > 6) {
					throw new InputMismatchException();
				}
				error = false;
			} catch (InputMismatchException e) {
				System.out.println("Your input was invalid. Try again.");
				scanner.next();
			}
		}
		return integer;
	}

	static void displaySignalPerAspect(Signal signal, String offset) {
		String gr = ".";
		String r = ".";
		String geH = ".";
		String geV = ".";
		String w = ".";
		String nextSpeed = "\u001b[1;33m" + signal.getNextSpeed() + "\u001b[0m";
		String speedLimit = signal.getSpeedLimit();

		if (signal.isSignalGreen()) {
			gr = "\u001b[1;32mO\u001b[0m";
		}
		if (signal.isSignalRed()) {
			r = "\u001b[1;31mO\u001b[0m";
		}
		if (signal.isSignalDoubleYellow()) {
			geH = "\u001b[1;33mO\u001b[0m";
			geV = "\u001b[1;33mO\u001b[0m";
		}
		if (signal.isSignalGreenYellowHorizontal()) {
			gr = "\u001b[1;32mO\u001b[0m";
			geH = "\u001b[1;33mO\u001b[0m";
		}
		if (signal.isSignalGreenYellowVertical()) {
			gr = "\u001b[1;32mO\u001b[0m";
			geV = "\u001b[1;33mO\u001b[0m";
		}
		if (signal.isSignalRedWhite()) {
			r = "\u001b[1;31mO\u001b[0m";
			w = "O";
		}

		if (signal.getActiveTrackIndex() == 0) {
			System.out.println(offset + "|\n" + offset + "|\n" + offset + "|");
		} else {
			System.out.println(offset + "  /\n" + offset + " /\n" + offset + "/");
		}

		String deadEnd = "    ";
		if (signal.getActiveTrack().getSignal() == null && !signal.isSignalRed())
			deadEnd = "|__|";
		else if(!signal.isSignalRed() && !signal.getActiveTrack().isDirection())
			deadEnd = " \\/ ";

		System.out.println("\n"+offset + deadEnd + nextSpeed);
		System.out.println(offset + gr + "   " + geH);
		System.out.println(offset + r);
		System.out.println(offset + geV);
		System.out.println(offset + w);
		System.out.println(offset + speedLimit);
		System.out.println();
	}

	static void displayAllSignalsPerAspect() {
		Signal formerSignal = new Signal("");

		for (Signal signal : signals) {
			System.out.println("Signal " + signal.getLoadId());
			if (formerSignal.getLoadId().startsWith(signal.getLoadId().substring(0, 1))) {
				displaySignalPerAspect(signal, "          ");
			} else {
				displaySignalPerAspect(signal, "");
			}
			formerSignal = signal;
		}
		System.out.println();
	}

	static void setTrackOccupied(Signal.Track track) {
		track.setOccupied(true);
	}

	static void setTrackFree(Signal signal) {
		for (Signal.Track track : signal.getTrackList()) {
			track.setOccupied(false);
		}
	}

	static void calculateSignalPosition(Signal yourSignal) {
		yourSignal.calculatePosition();
	}

	static void setNextSignal(Signal yourSignal, int trackIndex, Signal nextSignal) {
		yourSignal.getTrackList().get(trackIndex).setSignal(nextSignal);
	}

	static void setTrackActive(Signal signal, int trackIndex) {
		signal.setTrackActive(trackIndex);
	}

}
