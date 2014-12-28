package laxstats.query.sites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SiteServiceImpl {
	private SiteQueryRepository repository;

	@Autowired
	public void setRepository(SiteQueryRepository repository) {
		this.repository = repository;
	}

	public Map<String, String> getSiteData() {
		final Sort sort = getSiteSorter();
		final Iterable<SiteEntry> sites = repository.findAll(sort);
		final Map<String, String> result = new HashMap<>();
		for (final SiteEntry each : sites) {
			result.put(each.getId(), each.getName());
		}
		return result;
	}

	private Sort getSiteSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("address.region.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}
}
