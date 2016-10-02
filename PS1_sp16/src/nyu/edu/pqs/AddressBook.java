package nyu.edu.pqs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Address Book library will create an empty address book or load/read already exist data form
 * file. This class provides methods to add a new entry, remove entry, search for an entry by any
 * of the contact properties and to save the address book to a file.
 *
 * @author Nikita Amartya
 * @version 1.0
 */

public class AddressBook {
  private List<Contact> contactList;

  private AddressBook() {
    this.contactList = new ArrayList<Contact>();
  }

  /**
   * Creates an empty address book. Each address book has a name which is passed as parameter to
   * this method
   *
   * @param addressBookName
   *          type string, name of the Address book
   * @return empty address book
   */
  public static AddressBook createEmptyAddressBook() {
    return new AddressBook();
  }

  /**
   * Add a new contact into address book. Returns false if a contact by a given name already exists
   * or unable to add contact to the contactList.
   *
   * @param newContact
   *          Object of Contact to be added to Address book
   * @return true if new contact is added successfully
   */
  public boolean addEntry(Contact newContact) {
    boolean flag = true;
    for (Contact c : contactList) {
      if (c.getName().toString().equalsIgnoreCase(newContact.getName())) {
        flag = false;
      }
    }
    if (flag) {
      return this.contactList.add(newContact);
    } else {
      return false;
    }
  }

  /**
   * Removes contact from address book by name.
   *
   * @param removeContactName
   *          name of the Contact as String which needs to be removed from address book
   * @throws IllegalArgumentException
   *           if no contact by the given name is present
   */
  public void removeEntry(String removeContactName) {
    boolean success = false;
    ArrayList<Contact> toRemove = new ArrayList<Contact>();
    for (Contact c : contactList) {
      if (c.getName().toString().equalsIgnoreCase(removeContactName)) {
        toRemove.add(c);
      }
    }
    success = contactList.removeAll(toRemove);
    if (!success) {
      throw new IllegalArgumentException("Not found!");
    }
  }

  /**
   * Searches for an entry in the Address Book with the value of the keyword passed as argument.
   * Returns a list of Contact objects if one or more contacts are found that match the search
   * string. Otherwise an empty list is returned.
   *
   * @param searchString
   *          type String. Keyword to be searched for in Address Book
   * @return ArrayList of Contact
   */

  public ArrayList<Contact> searchEntry(String searchString) {
    ArrayList<Contact> results = new ArrayList<Contact>();
    for (Contact c : contactList) {
      if (c.toString().toLowerCase().contains(searchString.toLowerCase())) {
        results.add(c);
      }
    }
    return results;
  }

  /**
   * Save address book to file
   *
   * @param filePath
   *          the absolute path or the related path to the working directory of the saving file
   */
  public void saveAddressBook(String filePath) throws IOException {
    BufferedWriter writer;
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        file.createNewFile();
      }
      writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
      for (Contact c : contactList) {
        writer.write(c.toString());
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the address book from a file (passed as parameter) using Scanner. It loads the contacts
   * read from the file into the Address Book object calling this method.
   *
   * @param filePath
   *          the file path from where the contents have to be read.
   * @throws IOException
   *           throws an IOexception if there is some error with the file
   */
  public void readContactListFromFile(String filePath) throws IOException {
    File file = new File(filePath);
    Scanner readFile = new Scanner(file);
    String[] fields;
    String contact = "";

    while (readFile.hasNextLine()) {
      contact = readFile.nextLine();
      if (contact.length() > 0) {

        // split on the delimiter |
        fields = contact.split("\\|", -1);

        // building the contact object
        Contact.Builder buildNewContact = new Contact.Builder(fields[0], fields[1]);

        // setting email address
        if (fields[2].length() > 0) {
          buildNewContact.email(fields[2]);
        }

        // set address
        if (fields[3].length() > 0) {
          buildNewContact.address(fields[3]);
        }

        // set note
        if (fields[4].length() > 0) {
          buildNewContact.note(fields[4]);
        }

        // adding the contact to the address book
        Contact newContact = buildNewContact.build();
        addEntry(newContact);
      }
    }
    readFile.close();
  }

  /**
   * Overriding toString to put information related to each contact in a new line.
   */
  @Override
  public String toString() {
    String str = "";
    for (Contact c : contactList) {
      str = str + c.toString() + "\n";
    }
    return str;
  }

  /**
   * overriding equals for logical equality between two Address Book objects.
   */
  @Override
  public boolean equals(Object o) {
    // check if the o is reference to this
    if (o == this) {
      return true;
    }

    // check if o is of the Address Book type
    if (!(o instanceof AddressBook)) {
      return false;
    }

    // check if the contactList of both Address Books are equal
    AddressBook compareTo = (AddressBook) o;
    if (contactList == null) {
      if (compareTo.contactList != null) {
        return false;
      }
    } else if (!contactList.equals(compareTo.contactList)) {
      return false;
    }
    return true;
  }

  /**
   * hashCode method for Address Book
   */
  @Override
  public int hashCode() {
    int result = 31;
    result = (17 * result) + ((contactList == null) ? 0 : contactList.hashCode());
    return result;
  }

}
