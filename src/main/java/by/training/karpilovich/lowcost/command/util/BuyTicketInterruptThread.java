//package by.training.karpilovich.lowcost.command.util;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import javax.servlet.http.HttpSession;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import by.training.karpilovich.lowcost.command.Attribute;
//
//public class BuyTicketInterruptThread implements Runnable {
//
//	private static final Logger LOGGER = LogManager.getLogger(BuyTicketInterruptThread.class);
//
//	private static final int WAITING_TIME_IN_MINUTES = 1;
//
//	private CountDownLatch countDownlatch;
//	AtomicBoolean flag;
//
//	public BuyTicketInterruptThread(HttpSession session) {
//		countDownlatch = (CountDownLatch) session.getAttribute(Attribute.COUNT_DOWN_LATCH.toString());
//		flag = (AtomicBoolean) session.getAttribute(Attribute.BUYING_FLAG.toString());
//	}
//
//	@Override
//	public void run() {
//		boolean await = false;
//		try {
//			await = countDownlatch.await(WAITING_TIME_IN_MINUTES, TimeUnit.MINUTES);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		} finally {
//			flag.set(await);
//			LOGGER.debug(flag);
//		}
//	}
//}