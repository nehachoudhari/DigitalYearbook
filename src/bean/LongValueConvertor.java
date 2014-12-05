package bean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("longValueConverter")
public class LongValueConvertor implements Converter{
	public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        return Long.parseLong(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return value.toString();
    }
}