	//package net.di2e.dime.meme.doctracker;
	//
	//import org.slf4j.Logger;
	//import org.slf4j.LoggerFactory;
	//
	//import org.springframework.boot.CommandLineRunner;
	//import org.springframework.boot.SpringApplication;
	//import org.springframework.boot.WebApplicationType;
	//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
	//import org.springframework.boot.builder.SpringApplicationBuilder;
	//import org.springframework.context.ConfigurableApplicationContext;
	//import org.springframework.context.annotation.ComponentScan;
	//import org.springframework.context.annotation.PropertySource;
	//
	//@EnableAutoConfiguration
	//@ComponentScan
	//public class App implements CommandLineRunner {
	//	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	//
	//	private static String pathToDirectory = null;
	//
	//	private static boolean skipODrive = false;
	//
	//	private static boolean deleteDuplicates = false;
	//
	//	private static boolean processCmdLineArgs(String[] args) {
	//		int i = 0;
	//		try {
	//			for (; i < args.length; ++i) {
	//				if (args[i].compareToIgnoreCase("-deleteDuplicates") == 0) {
	//					deleteDuplicates = true;
	//					System.out.println("Option in effect: Deleting duplicates");
	//				}
	//				else if (args[i].compareToIgnoreCase("-pathToDirectory") == 0) {
	//					pathToDirectory = args[++i];
	//					System.out.println("Path to folder containing alfresco documents to migrate: " + pathToDirectory);
	//				}
	//				else if (args[i].compareToIgnoreCase("-skipODrive") == 0) {
	//					skipODrive = true;
	//					System.out.println("Option in effect: Skipping ODrive");
	//				}
	//				else {
	//					System.err.println(String.format("Unknown option: %s", args[i]));
	//					return false;
	//				}
	//			}
	//		}
	//		catch (IndexOutOfBoundsException e) {
	//			if (i > 0) { // if this wasn't the first arg, then we were advancing
	//				// unexpectedly
	//				System.err.println(String.format("Unknown value set for option: %s", args[i - 1]));
	//			}
	//			return false;
	//		}
	//
	//		if (pathToDirectory == null) {
	//			System.out.println("-pathToDirectory is required");
	//			return false;
	//		}
	//
	//		return true;
	//	}
	//
	//	private static void printUsage() {
	//		System.out.println("[-print-stats]: Required.");
	//		System.out.println("[-do-migration]: Optional.  Presence of this flag indicates to delete an existing product using the Product Service during prior to product creation.");
	//		System.out.println("[-skipODrive]: Optional.  If present, ODrive logic will be skipped during migration");
	//	}
	//
	//	@Override
	//	public void run(String... args) {
	//		LOGGER.info("EXECUTING : command line runner");
	////		migrationService.doMigrations(pathToDirectory);
	//
	//	}
	//
	//	public static void main(String[] args) {
	//
	//		if (!processCmdLineArgs(args)) {
	//			printUsage();
	//			return;
	//		}
	//
	//		SpringApplication springApplication =
	//			new SpringApplicationBuilder()
	//				.sources(App.class)
	//				.web(WebApplicationType.NONE)
	//				.build();
	//
	//		final ConfigurableApplicationContext appContext = springApplication.run(args);
	//	}
	//}
