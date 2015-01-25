package laxstats.web.site;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;

public class SiteForm {

	@NotNull
	@Size(min = 5, max = 100)
	private String name;
	private SiteStyle style;
	private Surface surface;
	private String directions;
	private String address1;
	private String address2;

	@NotNull
	@Size(min = 5, max = 30)
	private String city;

	@NotNull
	private Region region;
	private String postalCode;

	private List<SiteStyle> styles;
	private List<Surface> surfaces;
	private List<Region> regions;

	/*---------- Methods ----------*/

	public List<SiteStyle> getStyles() {
		if (styles == null) {
			final List<SiteStyle> list = new ArrayList<>();

			final SiteStyle[] arr = SiteStyle.values();
			final int len = arr.length;
			for (int i = 0; i < len; i++) {
				list.add(arr[i]);
			}
			styles = list;
		}
		return styles;
	}

	public List<Surface> getSurfaces() {
		if (surfaces == null) {
			final List<Surface> list = new ArrayList<>();

			final Surface[] arr = Surface.values();
			final int len = arr.length;
			for (int i = 0; i < len; i++) {
				list.add(arr[i]);
			}
			surfaces = list;
		}
		return surfaces;
	}

	public List<Region> getRegions() {
		if (regions == null) {
			final List<Region> list = new ArrayList<>();

			final Region[] arr = Region.values();
			final int len = arr.length;
			for (int i = 0; i < len; i++) {
				list.add(arr[i]);
			}
			regions = list;
		}
		return regions;
	}

	/*---------- Getter/Setters ----------*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SiteStyle getStyle() {
		return style;
	}

	public void setStyle(SiteStyle style) {
		this.style = style;
	}

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
