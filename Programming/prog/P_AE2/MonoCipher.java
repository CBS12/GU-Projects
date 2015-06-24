package P_AE2;

/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods 
 * to encode and decode a character.
 */
public class MonoCipher
{
	/** prepare instance variables
	 * 
	 */
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	
	private String key;
	

//-------------------------------------------------
	//prepare the constructor
	
	public MonoCipher(String k) {
	
		key = k;
		
		//create alphabet
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++){
			alphabet[i] = (char)('A' + i);
			System.out.print(alphabet[i]+" ");
		}
		System.out.println();
//-------------------------------------------------		
		/**
		 * Create the cipher array
		 */
		
		// create first part of cipher from keyword
		int len = key.length();
		cipher = new char [SIZE];
		
		int j = 0;
		for (j = 0; j<len;j++){
			cipher[j]= (key.charAt(j));
		}	
		// create remainder of cipher from the remaining 
		//characters of the alphabet int count = 0;		
		int i = j;
		while(j< SIZE){//loops cipher position
				char next = (char)('Z'-i+len);
						if(key.indexOf(next)==-1)
							//while next is not in key
						{
							cipher [j] = (char)(next);
							j++;
							i++;
						}
						else
						{
							i++;
						}
				}
		for(int t=0; t<SIZE;t++){
		System.out.print(cipher[t]+" ");//prints out a copy of the cipher
	}
		System.out.println();
	
	}
//-------------------------------------------------	
	/**Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)	{
		int pos = (ch - 'A');
		return cipher[pos];
	}
//-------------------------------------------------
	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
    public char decode(char ch)	{
    	
    	int index=findIndex(cipher, ch) ;
    	
    	if (index < 0){
    		System.err.println(ch + " is not in the cipher alphabet");
    	}
    	return alphabet[index];
    }
//-------------------------------------------------
    /**
     * @param char [] characters
     * @param c
     * @return int -1 if the character is not in the array, otherwise
     * the index of the character in the array.
     */
    private int findIndex(char [] characters, char c){
    		for (int i = 0; i < characters.length; i++) {
    			if (characters[i]==c) {
    				return i;
    			}
    		}
    		return -1;
    		}
    		
	
    			
}

