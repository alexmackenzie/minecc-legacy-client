package co.minecc.client;

public enum MCCColour {

	BLACK('0'),
	BLUE_DARK('1'),
	GREEN_DARK('2'),
	CYAN_DARK('3'),
	RED_DARK('4'),
	PURPLE('5'),
	GOLD('6'),
	GREY('7'),
	GREY_DARK('8'),
	BLUE('9'),
	GREEN('a'),
	CYAN('b'),
	RED('c'),
	PINK('d'),
	YELLOW('e'),
	WHITE('f'),
	
	SCRAMBLED('k'),
	BOLD('l'),
	STRIKE('m'),
	UNDERLINE('n'),
	ITALIC('o'),
	
	RESET('r');
	
	private static final String PREFIX = Character.toString((char) 0x00A7);
	
	private final char CHARACTER;
	
	MCCColour(char c)
	{
		CHARACTER = c;
	}
	
	@Override
	public String toString() 
	{
		return PREFIX + CHARACTER;
	}
	
}