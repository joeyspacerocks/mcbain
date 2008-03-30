package org.mcbain.examples.blog;

import java.util.Arrays;

import org.mcbain.Elemental;
import org.mcbain.Renderer;
import org.mcbain.Writer;
import org.mcbain.rest.Context;
import org.mcbain.template.Element;

public class DropDown implements Renderer, Elemental {

	private Iterable<Object> values;
	private Element element;
	
	
	public DropDown(Object... values) {
		this.values = Arrays.asList(values);
	}
	
	public DropDown(Iterable<Object> values) {
		this.values = values;
	}
	
	public void element(Element element) {
		this.element = element;
	}
	
	public void render(Context context, Writer writer) {
		writer.tag("select").attribute("name", element.id());
		
		for (Object value : values) {
			writer
				.tag("option")
				.print(value.toString(), false)
				.close();
		}
		
		writer.close();
	}
}
