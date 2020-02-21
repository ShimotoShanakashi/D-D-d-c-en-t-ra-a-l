
import java.util.List;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.json.JSONException;
import com.decentralbay.core.Blockchain;
import com.decentralbay.core.Config;
import com.decentralbay.webapi.MyWebSocketConn;

// CHECKING OPTIONS CHANGED FOR TESTING, CHECK OUT ISBLOCKVALID.CHECK().JAVA ON COM.BLOCKCHECKS.*

public class Main {
	static Blockchain blockchain;
	static Options options;
	final static Set<String> possibleCommands = new HashSet<String>(Arrays.asList("-y", "-n", "-help", "-config",
			"-show", "-initnode", "-webapi", "-all", "-default", "-automining", "start"));
	private final static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws IOException, JSONException {
		//title();
		List<String> argsList = new ArrayList<String>();
		List<Options> optsList = new ArrayList<Options>();
		List<String> doubleOptsList = new ArrayList<String>();

		Config cfg = new Config();

		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i].charAt(0)) {
				case '-':
					if (args[i].length() < 2)
						throw new IllegalArgumentException("Not a valid argument: " + args[i]);
					if (args[i].charAt(1) == '-') {
						if (args[i].length() < 3)
							throw new IllegalArgumentException("Not a valid argument: " + args[i]);
						// --opt
						doubleOptsList.add(args[i].substring(2, args[i].length()));
					} else {
						if (possibleCommands.contains(args[i].toLowerCase())) {
							if (args.length - 1 == i) {
								System.out.println("Expected arg after: " + args[i]);
								System.out.println("Type -help -all for showing the possible commands");
								break;
							}

							// default
							if (args[i].toLowerCase().equals("-initnode")
									&& args[i + 1].toLowerCase().equals("-default")) {
								blockchain = Blockchain.getSingletonBlockchain(args);
							}

							// automining
							if (args[i].toLowerCase().equals("-automining") && args[i + 1].toLowerCase().equals("-n")) {
								Config.automining = false;
								blockchain = Blockchain.getSingletonBlockchain(args);
							}
							if (args[i].toLowerCase().equals("-automining") && args[i + 1].toLowerCase().equals("-y")) {
								Config.automining = true;
								blockchain = Blockchain.getSingletonBlockchain(args);
							}

							if (args[i].toLowerCase().equals("-config") && args[i + 1].toLowerCase().equals("-show")) {
								logger.log(Level.INFO, cfg.toString());
							}

							// api server
							if (args[i].toLowerCase().equals("-webapi") && args[i + 1].toLowerCase().equals("-start")) {
								Thread wsThread = new Thread(new MyWebSocketConn());
								wsThread.start();
							}

							if (args[i].toLowerCase().equals("-help") && args[i + 1].toLowerCase().equals("-all")) {
								System.out.println("options:");
								System.out.println(
										"\t-initnode [-default]:\t\t initializes full node with default config");
								System.out.println("\t-automining [-y, -n]:\t\t sets automining node");
								System.out.println("\t-config [-show, -modify]:\t interacts with config file");
								System.out.println("\t-webapi [-start, -modify]:\t\t sets a webapi server");
							}

							optsList.add(new Options(args[i], args[i + 1]));
							i++;
						} else {
							System.out.println("Type -help -all for showing the possible commands");
						}
					}
					break;
				default:
					// arg
					argsList.add(args[i]);
					break;
				}
			}

		} else {
			System.out.println("Type -help -all for showing the possible commands");
		}

//		blockchain = Blockchain.getSingletonBlockchain(args);
//		Thread wsThread = new Thread(new MyWebSocketConn());
//		wsThread.start();			
		// type of node
		// if empty args = basic node configuration
		// advanced
		// api -port [int] -ip [server ip]
		// full node | -automining
	}

	private static void title() {
		// TODO Auto-generated method stub
		System.out.println();System.out.println();
		BufferedImage image = new BufferedImage(144, 32, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("Dialog", Font.PLAIN, 11));
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString("Decentral", 8, 24);


		for (int y = 0; y < 32; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < 144; x++)
				sb.append(image.getRGB(x, y) == -16777216 ? " " : image.getRGB(x, y) == -1 ? "#" : "*");
			if (sb.toString().trim().isEmpty())
				continue;
			System.out.println(sb);
		}
		System.out.println();System.out.println();
	}
}
