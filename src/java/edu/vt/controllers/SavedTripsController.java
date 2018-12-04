package edu.vt.controllers;

import edu.vt.EntityBeans.SavedTrips;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.SavedTripsFacade;
import edu.vt.globals.Methods;
import edu.vt.pojo.Food;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("savedTripsController")
@SessionScoped
public class SavedTripsController implements Serializable {

    @EJB
    private edu.vt.FacadeBeans.SavedTripsFacade ejbFacade;
    private List<SavedTrips> items = null;
    private SavedTrips selected;

    public SavedTripsController() {
    }

    public SavedTrips getSelected() {
        return selected;
    }

    public void setSelected(SavedTrips selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SavedTripsFacade getFacade() {
        return ejbFacade;
    }
    
//    Instance Methods

    public void saveTrip(String location, List<Food> restaurants) {
        SavedTrips newTrip = new SavedTrips();
        java.util.Date date = new java.util.Date(0,0,0);
        newTrip.setStartDate(date);
        newTrip.setEvents("");
        newTrip.setFlightCost(0);
        newTrip.setHotelCost(0);
        newTrip.setHotelName("");
        newTrip.setLocation(location);
        newTrip.setRestaurants(restaurants.toString());
        newTrip.setStartDate(date);
        newTrip.setStartLocation("");
        
        this.setSelected(newTrip);
        this.create();
    }
    
    public SavedTrips prepareCreate() {        
        /*
        Instantiate a new PublicVideo object and store its object reference into
        instance variable 'selected'. The PublicVideo class is defined in PublicVideo.java
         */
        selected = new SavedTrips();

        // Return the object reference of the newly created PublicVideo object
        return selected;
    }

    public void create() {        
        /*
        We need to preserve the messages since we will redirect to show a
        different JSF page after successful creation of the publicVideo.
         */
        Methods.preserveMessages();
        /*
        Show the message "PublicVideo was Successfully Created!" given in
        the Bundle.properties file under the PublicVideoCreated keyword.

        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SavedTripsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SavedTripsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SavedTripsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SavedTrips> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public SavedTrips getSavedTrips(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<SavedTrips> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SavedTrips> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SavedTrips.class)
    public static class SavedTripsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SavedTripsController controller = (SavedTripsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "savedTripsController");
            return controller.getSavedTrips(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SavedTrips) {
                SavedTrips o = (SavedTrips) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SavedTrips.class.getName()});
                return null;
            }
        }

    }

}
