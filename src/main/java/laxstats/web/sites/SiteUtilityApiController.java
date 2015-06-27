package laxstats.web.sites;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;

@RestController
public class SiteUtilityApiController {

   @RequestMapping(value = "/admin/api/siteStyles", method = RequestMethod.GET)
   public Set<SiteStyle> styleIndex() {
      return EnumSet.allOf(SiteStyle.class);
   }

   @RequestMapping(value = "/admin/api/surfaces", method = RequestMethod.GET)
   public Set<Surface> surfaceIndex() {
      return EnumSet.allOf(Surface.class);
   }

   @RequestMapping(value = "/admin/api/regions", method = RequestMethod.GET)
   public Set<Region> regionIndex() {
      return EnumSet.allOf(Region.class);
   }
}
