package laxstats.query.people;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import laxstats.web.people.SearchPeopleForm;

import org.springframework.data.jpa.domain.Specification;

public class PersonSpecifications {

	public static Specification<PersonEntry> search(SearchPeopleForm form) {
		return new Specification<PersonEntry>() {

			@Override
			public Predicate toPredicate(Root<PersonEntry> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				final boolean hasFirstName = form.getFirstName() != null
						&& form.getFirstName().length() > 0;
				final boolean hasLastName = form.getLastName() != null
						&& form.getLastName().length() > 0;

				// The first name parameter is optional
				Predicate likeFirstName = null;
				if (hasFirstName) {
					final String fn = getLikePattern(form.getFirstName());
					likeFirstName = cb
							.like(cb.lower(root.get("firstName")), fn);
				}

				// The last name parameter is not optional. Search on all
				// records if lastName is null.
				String ln = "";
				if (hasLastName) {
					ln = getLikePattern(form.getLastName());
				}
				final Predicate likeLastName = cb.like(
						cb.lower(root.get("lastName")), ln);

				// Return the predicate
				if (likeFirstName != null) {
					if (!hasLastName) {
						return likeFirstName;
					} else {
						return cb.and(likeFirstName, likeLastName);
					}
				}
				return likeLastName;
			}
		};
	}

	private static String getLikePattern(String searchTerm) {
		final StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(searchTerm.toLowerCase());
		sb.append("%");
		return sb.toString();
	}
}
