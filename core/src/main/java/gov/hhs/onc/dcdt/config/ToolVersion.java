package gov.hhs.onc.dcdt.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.log4j.Logger;

public class ToolVersion
{
	private final static String[] PROP_KEY_NODES_PREFIX = ArrayUtils.toArray("dcdt");
	private final static String[] GROUP_ID_PROP_KEY_NODES = ArrayUtils.add(PROP_KEY_NODES_PREFIX, "groupId");
	private final static String[] ARTIFACT_ID_PROP_KEY_NODES = ArrayUtils.add(PROP_KEY_NODES_PREFIX, "artifactId");
	private final static String[] VERSION_PROP_KEY_NODES = ArrayUtils.add(PROP_KEY_NODES_PREFIX, "version");
	private final static String[] NAME_PROP_KEY_NODES = ArrayUtils.add(PROP_KEY_NODES_PREFIX, "name");
	private final static String[] DESC_PROP_KEY_NODES = ArrayUtils.add(PROP_KEY_NODES_PREFIX, "description");
	
	private final static String[] BUILD_TIMESTAMP_PROP_KEY_NODES = ArrayUtils.addAll(PROP_KEY_NODES_PREFIX, "build", "timestamp");
	private final static String[] BUILD_TIMESTAMP_FORMAT_PROP_KEY_NODES = ArrayUtils.add(BUILD_TIMESTAMP_PROP_KEY_NODES, "format");
	
	private final static String[] VERSION_HG_PROP_KEY_NODES_PREFIX = ArrayUtils.add(VERSION_PROP_KEY_NODES, "hg");
	private final static String[] VERSION_HG_AUTHOR_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "author");
	private final static String[] VERSION_HG_BRANCH_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "branch");
	private final static String[] VERSION_HG_DATE_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "date");
	private final static String[] VERSION_HG_DATE_FORMAT_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "format");
	private final static String[] VERSION_HG_REVISION_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "revision");
	private final static String[] VERSION_HG_TAG_PROP_KEY_NODES = ArrayUtils.add(VERSION_HG_PROP_KEY_NODES_PREFIX, "tag");
    
	private final static Pattern GROUP_ID_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	private final static Pattern ARTIFACT_ID_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	private final static Pattern VERSION_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	private final static Pattern NAME_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	private final static Pattern DESC_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	
	private final static Pattern BUILD_TIMESTAMP_PATTERN = Pattern.compile("^\\s*(.+)\\s*$");
	
	private final static Pattern VERSION_HG_AUTHOR_PATTERN = 
		Pattern.compile("^\\s*\\$Author:\\s+(.+)\\s+\\$\\s*$");
    private final static Pattern VERSION_HG_BRANCH_PATTERN = 
		Pattern.compile("^\\s*\\$Branch:\\s+(.+)\\s+\\$\\s*$");
	private final static Pattern VERSION_HG_DATE_PATTERN = 
		Pattern.compile("^\\s*\\$Date:\\s+(\\d{4}\\-\\d{2}\\-\\d{2})T(\\d{2}:\\d{2}:\\d{2})(\\-?\\d{2}:\\d{2}).+$");
	private final static Pattern VERSION_HG_REVISION_PATTERN = 
		Pattern.compile("^\\s*\\$Revision:\\s+([\\da-f]+)\\s+\\$\\s*$");
    private final static Pattern VERSION_HG_TAG_PATTERN = 
		Pattern.compile("^\\s*\\$Tag:\\s+(.+)\\s+\\$\\s*$");
	
	private final static SimpleDateFormat BUILD_TIMESTAMP_FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	private final static SimpleDateFormat HG_DATE_FORMAT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss XXX");
	private final static SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	
	private final static Logger LOGGER = Logger.getLogger(ToolVersion.class);
	
	private ToolConfig config;
	private String moduleName;
	
	public ToolVersion(ToolConfig config, String moduleName)
	{
		this.config = config;
		this.moduleName = moduleName;
	}
	
	public String getGroupId()
	{
		return this.getPropValue(GROUP_ID_PROP_KEY_NODES, GROUP_ID_PATTERN);
	}
	
	public String getArtifactId()
	{
		return this.getPropValue(ARTIFACT_ID_PROP_KEY_NODES, ARTIFACT_ID_PATTERN);
	}
	
	public String getVersion()
	{
		return this.getPropValue(VERSION_PROP_KEY_NODES, VERSION_PATTERN);
	}
	
	public String getName()
	{
		return this.getPropValue(NAME_PROP_KEY_NODES, NAME_PATTERN);
	}
	
	public String getDescription()
	{
		return this.getPropValue(DESC_PROP_KEY_NODES, DESC_PATTERN);
	}
	
	public String getBuildTimestamp()
	{
		return this.getDateDisplayValue(this.getDateFormatPropValue(BUILD_TIMESTAMP_FORMAT_PROP_KEY_NODES, 
			BUILD_TIMESTAMP_FORMAT_DEFAULT), BUILD_TIMESTAMP_PROP_KEY_NODES, BUILD_TIMESTAMP_PATTERN);
	}
	
	public String getHgAuthor()
	{
		return this.getPropValue(VERSION_HG_AUTHOR_PROP_KEY_NODES, VERSION_HG_AUTHOR_PATTERN);
	}
    
    public String getHgBranch()
	{
		return this.getPropValue(VERSION_HG_BRANCH_PROP_KEY_NODES, VERSION_HG_BRANCH_PATTERN);
	}
	
	public String getHgDate()
	{
		return this.getDateDisplayValue(this.getDateFormatPropValue(VERSION_HG_DATE_FORMAT_PROP_KEY_NODES, 
			HG_DATE_FORMAT_DEFAULT), VERSION_HG_DATE_PROP_KEY_NODES, VERSION_HG_DATE_PATTERN);
	}
	
	public String getHgRevision()
	{
		return this.getPropValue(VERSION_HG_REVISION_PROP_KEY_NODES, VERSION_HG_REVISION_PATTERN);
	}
    
    public String getHgTag()
	{
		return this.getPropValue(VERSION_HG_TAG_PROP_KEY_NODES, VERSION_HG_TAG_PATTERN);
	}
	
	private String getDateDisplayValue(SimpleDateFormat dateFormat, String[] datePropKeyNodes, Pattern datePropValuePattern)
	{
		Date date = this.getDatePropValue(dateFormat, datePropKeyNodes, datePropValuePattern);
		
		return (date != null) ? DISPLAY_DATE_FORMAT.format(date) : null;
	}
	
	private Date getDatePropValue(SimpleDateFormat dateFormat, String[] datePropKeyNodes, Pattern datePropValuePattern)
	{
		if (dateFormat == null)
		{
			return null;
		}
		
		String datePropKey = this.buildPropKey(datePropKeyNodes), 
			datePropValue = this.getPropValue(datePropKey, datePropValuePattern);
		
		if (datePropValue != null)
		{
			try
			{
				return dateFormat.parse(datePropValue);
			}
			catch (ParseException e)
			{
				LOGGER.error("Date property (key=" + datePropKey + ") value is invalid for date format pattern (" + 
					dateFormat.toPattern() + "): " + datePropValue, e);
			}
		}
		
		return null;
	}
	
	private SimpleDateFormat getDateFormatPropValue(String[] dateFormatPropKeyNodes, SimpleDateFormat dateFormatDefault)
	{
		String dateFormatPropKey = this.buildPropKey(dateFormatPropKeyNodes), 
			dateFormatPropValue = this.getPropValue(dateFormatPropKey, null);
		
		if (dateFormatPropValue != null)
		{
			try
			{
				return new SimpleDateFormat(dateFormatPropValue);
			}
			catch (IllegalArgumentException e)
			{
				LOGGER.error("Date format property (key=" + dateFormatPropKey + ") value is invalid: " + 
					dateFormatPropValue, e);
			}
		}
		
		return dateFormatDefault;
	}
	
	private String getPropValue(String[] propKeyNodes, Pattern propValuePattern)
	{
		return this.getPropValue(this.buildPropKey(propKeyNodes), propValuePattern);
	}
	
	private String getPropValue(String propKey, Pattern propValuePattern)
	{
		String propValue;
		
		try
		{
			propValue = this.config.getConfig().getString(propKey);
		}
		catch (NoSuchElementException e)
		{
			return null;
		}
		
		if (propValuePattern == null)
		{
			return null;
		}
		
		Matcher matcher;
		
        if ((propValue != null) && (matcher = propValuePattern.matcher(propValue)).matches())
        {
            StrBuilder propValueBuilder = new StrBuilder();
            
            for (int a = 1; a <= matcher.groupCount(); a++)
            {
                propValueBuilder.appendSeparator(" ");
                propValueBuilder.append(matcher.group(a));
            }
            
            return propValueBuilder.toString();
        }
        else
        {
            return null;
        }
	}
	
	private String buildPropKey(String[] propKeyNodes)
	{
		return XpathBuilder.buildNodeNames(ArrayUtils.add(propKeyNodes, 1, this.moduleName));
	}

	public String getModuleName()
	{
		return this.moduleName;
	}
}