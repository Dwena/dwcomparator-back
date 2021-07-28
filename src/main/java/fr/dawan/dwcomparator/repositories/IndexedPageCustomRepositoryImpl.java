package fr.dawan.dwcomparator.repositories;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import fr.dawan.dwcomparator.entities.IndexedPage;

@Repository
public class IndexedPageCustomRepositoryImpl implements IndexedPageCustomRepository {

	@PersistenceContext
	private EntityManager em; // objet connexion + dat

	public static boolean checkEmptyArray(String[] strArray) {
		boolean isEmpty = true;
		for (String s : strArray) {
			if (!s.trim().equals("")) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}

	// URL :
	// /api/indexedpages/search-by/1/1/20?price_min=30&price_max=120&audience=admin&keyword=java&order=price+ASC&trainingcenter=12
	// paramsMap :
	// ?price_min=30&price_max=120&audience=admin&keyword=java&order=price_ASC&order=creationDate_DESC
	private Query getQueryBy(String req, Map<String, String[]> paramsMap, boolean published) {
		StringBuilder orderPart = new StringBuilder();
		StringBuilder jpqlRequest = new StringBuilder(req);

		for (String param : paramsMap.keySet()) {
			if (!checkEmptyArray(paramsMap.get(param))) {
				String cleanParamName = param;
				if (param.endsWith("_min")) {
					cleanParamName = param.substring(0, param.length() - 4);
					jpqlRequest.append(" AND ip." + cleanParamName + ">= :" + param);
				} else if (param.endsWith("_max")) {
					cleanParamName = param.substring(0, param.length() - 4);
					jpqlRequest.append(" AND ip." + cleanParamName + "<= :" + param);
				} else if (param.equals("keyword")) {
					jpqlRequest.append(" AND (ip.title like :" + param + " OR ip.trainingProgram like :" + param
							+ " OR ip.objectives like :" + param + ") ");
				} else if (param.equals("trainingcenter")) {
					jpqlRequest.append(" AND ip.crawler.trainingCenter.id = :" + param);
				} else if (param.equals("order")) {
					orderPart.append(" ORDER BY ");
					String[] orderValues = paramsMap.get(param);
					for (int i = 0; i < orderValues.length; i++) {
						String[] orderArr = orderValues[i].split("_"); // audience_ASC
						orderPart.append(" ip." + orderArr[0] + " " + orderArr[1]);
						if (i < orderValues.length - 1)
							orderPart.append(", ");
					}
				} else {
					jpqlRequest.append(" AND ip." + cleanParamName + " LIKE :" + param);
				}
			}
		}

		if (published) {
			jpqlRequest.append(" AND ip.published=true");
		}

		if (orderPart != null && !orderPart.toString().trim().equals("")) {
			jpqlRequest.append(" " + orderPart.toString());
		}
		System.out.println("REQ : " + jpqlRequest.toString());
		Query query = em.createQuery(jpqlRequest.toString());
		for (String param : paramsMap.keySet()) {
			if (!checkEmptyArray(paramsMap.get(param))) {
				try {
					String cleanParamName = param;
					if (!param.equals("order")) {
						if (param.endsWith("_min")) {
							cleanParamName = param.substring(0, param.length() - 4);
						} else if (param.endsWith("_max")) {
							cleanParamName = param.substring(0, param.length() - 4);
						}
						Class<?> paramType = String.class;
						if (param.equals("trainingcenter")) {
							paramType = long.class;
						} else if (!param.equals("keyword") && !param.equals("trainingcenter")) {
							try {
								Field f = IndexedPage.class.getDeclaredField(cleanParamName);
								f.setAccessible(true);
								paramType = f.getType();
							} catch (Exception ex) {
								// ignore exception => String instead
							}
						}
						//System.out.println("class field : " + param + " :::> " + paramType);

						if (paramType.equals(String.class)) {
							query.setParameter(param, "%" + paramsMap.get(param)[0] + "%");
						} else if (paramType.equals(float.class)) {
							query.setParameter(param, Float.parseFloat(paramsMap.get(param)[0]));
						} else if (paramType.equals(double.class)) {
							query.setParameter(param, Double.parseDouble(paramsMap.get(param)[0]));
						} else if (paramType.equals(boolean.class)) {
							query.setParameter(param, Boolean.parseBoolean(paramsMap.get(param)[0]));
						} else if (paramType.equals(long.class)) {
							query.setParameter(param, Long.parseLong(paramsMap.get(param)[0]));
						} // TODO if other types
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return query;
	}

	@Override
	public long countByCriterias(Map<String, String[]> paramsMap, boolean published) {
		String req = "SELECT COUNT(ip.id) FROM IndexedPage ip WHERE ip.id>0";
		// param_min, param_max
		Query query = getQueryBy(req, paramsMap, published);
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexedPage> searchBy(Map<String, String[]> paramsMap, int page, int size, boolean published) {
		String req = "SELECT ip FROM IndexedPage ip WHERE ip.id>0";
		// param_min, param_max
		Query query = getQueryBy(req, paramsMap, published);
		query.setFirstResult(page * size).setMaxResults(size);
		return (List<IndexedPage>) query.getResultList();

	}

}
