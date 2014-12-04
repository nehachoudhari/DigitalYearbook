package bean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("valueConverter")
public class ValueConvertor implements Converter{
	public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        return Integer.parseInt(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return value.toString();
    }
}