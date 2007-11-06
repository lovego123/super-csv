package org.supercsv.cellprocessor;

import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.exception.SuperCSVException;
import org.supercsv.util.CSVContext;

/**
 * Ensure that Strings or String-representations of objects has a maximum size
 * 
 * @author Kasper B. Graversen
 */
public class Trim extends CellProcessorAdaptor implements StringCellProcessor {
	int maxSize;
	String trimPostfix = "";

	/** Trim strings to ensure a maximum size */
	public Trim(final int maxSize) {
		super(NullObjectPattern.INSTANCE);
		if(maxSize < 1) throw new SuperCSVException("argument maxSize must be > 0");
		this.maxSize = maxSize;
	}

	/**
	 * Trim strings to ensure a maximum size. If a string is trimmed, it will get the <code>trimPostfix</code> string
	 * appended (e.g. "..." to show it has been trimmed)
	 */
	public Trim(final int maxSize, final String trimPostfix) {
		this(maxSize);
		this.trimPostfix = trimPostfix;
	}

	/**
	 * Trim strings to ensure a maximum size. If a string is trimmed, it will get the <code>trimPostfix</code> string
	 * appended (e.g. "...")
	 */
	public Trim(final int maxSize, final String trimPostfix, final StringCellProcessor next) {
		this(maxSize, next);
		this.trimPostfix = trimPostfix;
	}

	/** Trim strings to ensure a maximum size */
	public Trim(final int maxSize, final StringCellProcessor next) {
		super(next);
		this.maxSize = maxSize;
	}

	/**
	 * @throws SuperCSVException
	 *             when the value is some value that cannot be translated into a boolean value {@inheritDoc}
	 */
	@Override
	public Object execute(final Object value, final CSVContext context) {
		final String sval = value.toString(); // cast

		String result;
		if(sval.length() <= maxSize)
			result = sval;
		else
			result = sval.substring(0, maxSize) + trimPostfix;

		return next.execute(result, context);
	}
}