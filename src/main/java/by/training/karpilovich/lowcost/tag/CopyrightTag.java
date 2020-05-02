package by.training.karpilovich.lowcost.tag;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CopyrightTag extends TagSupport {

	private static final String OUT_STRING_FORMAT = "%d %s";
	private static final String COMPANY_NAME = "Lowcost Airlines";
	private static final Logger LOGGER = LogManager.getLogger(CopyrightTag.class);
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		try {
			getWriter().print(getOutString());
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return SKIP_BODY;
	}

	private JspWriter getWriter() {
		return pageContext.getOut();
	}

	private String getOutString() {
		return String.format(OUT_STRING_FORMAT, getCurrentYear(), COMPANY_NAME);
	}

	private int getCurrentYear() {
		return new GregorianCalendar().get(Calendar.YEAR);
	}
}