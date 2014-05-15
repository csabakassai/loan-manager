package hu.bme.aut.loanmanager.pages;

import hu.bme.loanmanager.domain.io.UserVO;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


@AuthorizeInstantiation("admin")
public class AdminPage extends AbstractLoggedInPage {

	@Override
	public String getPageName() {
		return "Admin page";
	}
	
	public AdminPage() {
		List<UserVO> users = manager.loadAllUser();
		add(new ListView<UserVO>("users", users) {

			@Override
			protected void populateItem(ListItem<UserVO> item) {
				UserVO userVO = item.getModelObject();
				item.add(new Label("userName", userVO.getUsername()));
				item.add(new Label("fullName", userVO.getFullName()));
				item.add(new Label("password", userVO.getPassword()));
				item.add(new Label("emailAddress", userVO.getEmail()));
				item.add(new Label("admin", userVO.isAdmin()));
				
			}
		});
		
	}
	
}
