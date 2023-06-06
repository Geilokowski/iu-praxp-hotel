package org.iu.study.hotel.utilities;

public interface NotificationMessages {
    String ENTITY_NOT_FOUND_ERROR = """
                            Leider gab es ein Problem mit dem von ihnen aufgerufenen Link,\s
                            bitte nutzen Sie zur Nutzerverwaltung die Tabelle.
                            """;
    String INVALID_URL_PARAM_ERROR = """
                Leider konnten wir Ihre Anfrage nicht verarbeiten,\s
                bitte nutzen Sie die Tabelle unten um die Kunden zu verwalten.""";
    String SAVED_CUSTOMER_SUCCESSFULLY = "Kunde erfolgreich gespeichert";
    String SAVED_BOOKING_SUCCESSFULLY = "Buchung erfolgreich gespeichert";
    String CREATED_CUSTOMER_SUCCESSFULLY = "Kunde erfolgreich erstellt";
    String CREATED_BOOKING_SUCCESSFULLY = "Buchung erfolgreich erstellt";
    String DELETED_CUSTOMER_SUCCESSFULLY = "Kunde erfolgreich erstellt";
    String DELETED_BOOKING_SUCCESSFULLY = "Buchung erfolgreich gelöscht";
}
