package JavaBall;


/**
 * A program to generate a bar chart using rectangle components
 * in a JFrame. The vertical height represents number of matches,
 * horizontal provides refereeID. After the frame size is set and 
 * the title and labels heights are set the remaining space is allocated
 * to bar height (rectangle height). The maximum value(int number of matches) 
 * is used to set the vertical scale (double), this is then used to calculate
 * the drawn bar heights and from there the position of the rectangles
 * is calculated.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;


public class Chart extends JComponent 
{
	//instance variables
	private int[] numMatches;//number of matches refereed so far.
	private String[] refID;//
	private String title, xLabel;//chart title and label on the X- axis
	private final int SPACE = 15; //space above and below text,labels,values and title


	private final int BAR_SPACE = 2;//sets the spacing between the bars. Q

	/*
	 * constructor for the Chart
	 */
	public Chart(int[] v, String[] n, String t, String x) 
	{
		refID = n;
		numMatches = v;
		title = t;
		xLabel = x;
	}
	//------------------------------------------------------------------------------
	/*
	 * Create rectangles to represent bars in a bar chart
	 */
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;

		if (numMatches == null || numMatches.length == 0)//no graph to draw
			return;

		/*finding the range of values (number of matches). Max needed to 
		establish scale for the vertical axis*/

		double maxValue = 0;
		for (int i = 0; i < numMatches.length; i++) 
		{
			if (maxValue < numMatches[i])
				maxValue = numMatches[i];
		}
		/*using this method to monitor resizing of the frame and proportional
		resizing of the chart*/

		int chartWidth = this.getWidth();
		int chartHeight = this.getHeight();
		int barWidth = chartWidth / numMatches.length;

		/*
		 * Font metrics used to get measurements of strings; lengths
		 * and heights.
		 */

		Font titleFont = new Font("Candara", Font.BOLD, 20);//chart title font
		FontMetrics titleFontMetrics = g2.getFontMetrics(titleFont);
		Font labelFont = new Font("Candara", Font.PLAIN, 10);//bar id label fonts
		FontMetrics labelFontMetrics = g2.getFontMetrics(labelFont);
		Font numberFont = new Font("Candara", Font.PLAIN, 10);//bar value label font
		Font xLabelFont = new Font("Candara", Font.PLAIN, 14);//x-axis label font
		FontMetrics xLabelFontMetrics = g2.getFontMetrics(xLabelFont);

		/* 
		 * positioning the title of the chart. 
		 */
		int titleWidth = titleFontMetrics.stringWidth(title);//width of title string
		int y = titleFontMetrics.getHeight() + SPACE;//position of the title on the y-axis
		int x = (chartWidth - titleWidth) / 2;//central position of the title on the x-axis
		g2.setFont(titleFont);//set the font
		g2.drawString(title, x, y);//draw the chart title at position x and y.

		/*
		 * the following provides the y-values/height allowances which are required
		 */
		int top = titleFontMetrics.getHeight()+ 2*SPACE;//to allow space between the top of chart and bar
		int axis = labelFontMetrics.getHeight() + SPACE;//labels on each bar - height allowance
		int bottom = xLabelFontMetrics.getHeight();//x-axis label - height allowance


		/* 
		 * Calculating the scale of the chart by finding the remaining available frame space
		 * 
		 */
		double scale = (chartHeight - top - axis - bottom) / (maxValue);
		y = chartHeight - SPACE;
		x = (chartWidth - xLabelFontMetrics.stringWidth(xLabel))/2;
		g2.setFont(xLabelFont);
		g2.drawString(xLabel, x, y);

		/*
		 * process to draw and label the bars/rectangles to represent the data.
		 *The y value for each bar is calculated by subtracting barHeight from the baseline
		 * 
		 */
		g2.setFont(labelFont);
		g2.setFont(numberFont);
		//iterate through the array for each bar
		for (int i = 0; i < numMatches.length; i++) 
		{
			int barX = i * barWidth;//positions each bar on the x-axis
			int barY = top;//positioned initially below the title for the top
			int barHeight = (int) (numMatches[i] * scale);//calculate actual height of rectangle

			//calculate the y-value for the top of the rectangle
			barY += (int) ((maxValue - numMatches[i]) * scale);

			//sets colour and draws the rectangles
			g2.setColor(Color.yellow);
			g2.fillRect(barX, barY, barWidth - BAR_SPACE, barHeight);
			g2.setColor(Color.black);
			g2.drawRect(barX, barY, barWidth - BAR_SPACE, barHeight);

			//sets labels on the x-axis below the rectangles with an x-axis label below that
			//the refID and numMatch labels are centred 
			x = i * barWidth + (barWidth - labelFontMetrics.stringWidth(refID[i])) / 2;
			y = chartHeight - xLabelFontMetrics.getHeight()- SPACE;
			//axis label below each bar at position (x,y)
			g2.drawString(refID[i], x, y);
			//NumMatches value above the bar at the same x position
			g2.drawString(String.format("%d",numMatches[i]), x, barY - BAR_SPACE);
		}
	}

}