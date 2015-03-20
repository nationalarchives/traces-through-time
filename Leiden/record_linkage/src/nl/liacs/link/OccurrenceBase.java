package nl.liacs.link;
/**
 * OccurrenceBase class includes the accepted fields/attributes specified in the documentation.
 * 
 * NOTE: if new fields must be added, simply add them here. It will instruct
 * GSON to scan input files for it and the retrieved values are added to the Occurrence.
 * 
 * @author Benjamin
 * @author Kleanthi
 */

public final class OccurrenceBase {
    public String origOccurrence = "";
    public String title = "";
    public String forename = "";
    public String article = "";
    public String surname = "";
    public String role = "";
    public String provenance = "";
    public String childOf = "";
    public String gender = "";
    public String appearanceDate = "";
    public String id = "";
    public String sentenceId = "";
    public String sectionId = "";
    public String fileId = "";
	
}
