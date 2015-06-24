package Qu3;




public class Qu3 {
	
	
	public static void main(String[] args) {
		
		// Make some Standard leaf and composite things
		FilingComponent doc1 = new DocLeaf("Date1",28);
		FilingComponent doc2 = new DocLeaf("Date2",320);
		FilingComponent doc3 = new DocLeaf("Holiday",60);
		FilingComponent doc4 = new DocLeaf("Christmas",28);
		FilingComponent doc5 = new DocLeaf("Back To School",320);
		FilingComponent doc6 = new DocLeaf("Birthdays",60);
		
		FolderComposite diary = new FolderComposite("Diary");
		FolderComposite lists = new FolderComposite("List");
		FolderComposite letters = new FolderComposite("Letters");

		diary.add(new DocLeaf("Date1",200));
		lists.add(new DocLeaf("Holiday",85));
		letters.add(new DocLeaf("Christmas Thank you's",56));
		letters.add(doc5);
		letters.add(doc4);

		lists.add(new DocLeaf("Easter",56));
		diary.add(lists);
		letters.add(diary);
		

		
		// Do some displaying

		System.out.println(doc1.display());
		System.out.println(doc3.display());
		System.out.println(doc6.display());
		System.out.println(diary.display());
		System.out.println(lists.display());
		System.out.println(diary.totSize());
		System.out.println(diary.totNumber());
		System.out.println(letters.display());

	}
}