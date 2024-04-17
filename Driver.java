import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Driver extends Application {
	private File file;
	private DoubleLinkedList districts;
	private BorderPane pane;
	// private Node prevNode;
	private Node distNode;
	private SNode node;
	private TableView<Martyr> table;

	@Override
	public void start(Stage stage) {
		table = new TableView<Martyr>();
		districts = new DoubleLinkedList();
		pane = new BorderPane();

		Label welcome = new Label("Martyrs of Palestine");

		welcome.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-style: italic");
		pane.setCenter(welcome);

		TableColumn<Martyr, String> tName = new TableColumn<Martyr, String>("name");
		TableColumn<Martyr, Byte> tAge = new TableColumn<Martyr, Byte>("age");
		TableColumn<Martyr, String> tDate = new TableColumn<Martyr, String>("date");
		TableColumn<Martyr, String> tLoc = new TableColumn<Martyr, String>("location");
		TableColumn<Martyr, String> tDist = new TableColumn<Martyr, String>("district");
		TableColumn<Martyr, Character> tGender = new TableColumn<Martyr, Character>("gender");

		table.getColumns().add(tName);
		table.getColumns().add(tAge);
		table.getColumns().add(tDate);
		table.getColumns().add(tLoc);
		table.getColumns().add(tDist);
		table.getColumns().add(tGender);

		tName.setCellValueFactory(new PropertyValueFactory<Martyr, String>("name"));
		tAge.setCellValueFactory(new PropertyValueFactory<Martyr, Byte>("age"));
		tDate.setCellValueFactory(new PropertyValueFactory<Martyr, String>("date"));
		tLoc.setCellValueFactory(new PropertyValueFactory<Martyr, String>("location"));
		tDist.setCellValueFactory(new PropertyValueFactory<Martyr, String>("district"));
		tGender.setCellValueFactory(new PropertyValueFactory<Martyr, Character>("gender"));

		pane.setLeft(table);

		/* To make a menu bar and add menus to it for District and Location */
		MenuBar bar = new MenuBar();
		Menu open = new Menu("file");
		Menu districtMenu = new Menu("District");
		Menu locationMenu = new Menu("Location");
		bar.getMenus().addAll(open, districtMenu, locationMenu);

		/* To open a file to read from it */
		MenuItem openFile = new MenuItem("open file");
		open.getItems().add(openFile);
		openFile.setOnAction(e -> {
			FileChooser choose = new FileChooser();
			file = choose.showOpenDialog(stage);
			if (file != null) {
				read();
			}
		});

		/* To add menu items for adding deleting editing and navigating the districts */
		MenuItem addDistrict = new MenuItem("add district");
		MenuItem deleteDistrict = new MenuItem("remove district");
		MenuItem updateDistrict = new MenuItem("update district");
		MenuItem navigateDistrict = new MenuItem("navigate districts");
		MenuItem dateMaxMart = new MenuItem("date with max martyrs");
		MenuItem printOnFile = new MenuItem("print district on file");

		districtMenu.getItems().addAll(addDistrict, deleteDistrict, updateDistrict, navigateDistrict, dateMaxMart,
				printOnFile);

		/* Setting actions for the menu items in District */
		addDistrict.setOnAction(e -> addDistrictScene());
		deleteDistrict.setOnAction(e -> deleteDistrict());
		updateDistrict.setOnAction(e -> updateDistrict());
		navigateDistrict.setOnAction(e -> navigateDistrict());
		dateMaxMart.setOnAction(e -> dateMaxMart());
		printOnFile.setOnAction(e -> printFile());

		/* To add menu for adding deleting and updating the locations and martyrs */
		MenuItem addLocation = new MenuItem("add location");
		MenuItem deleteLocation = new MenuItem("delete location");
		MenuItem updateLocation = new MenuItem("update location");
		MenuItem navigateLocation = new MenuItem("navigate locations");
		MenuItem searchLocation = new MenuItem("search location");
		MenuItem addMartyr = new MenuItem("add martyr");
		MenuItem deleteMartyr = new MenuItem("delete martyr");
		MenuItem updateMartyr = new MenuItem("update martyr");
		MenuItem searchMartyr = new MenuItem("search martyr");

		locationMenu.getItems().addAll(addLocation, deleteLocation, updateLocation, navigateLocation, searchLocation,
				addMartyr, deleteMartyr, updateMartyr, searchMartyr);

		/* Setting actions for the menu items in Location */
		addLocation.setOnAction(e -> addLocationScene());
		deleteLocation.setOnAction(e -> deleteLocation());
		updateLocation.setOnAction(e -> updateLocation());
		navigateLocation.setOnAction(e -> navigateLocation());
		searchLocation.setOnAction(e -> searchLocation());
		addMartyr.setOnAction(e -> addMartyrScene());
		deleteMartyr.setOnAction(e -> deleteMartyr());
		updateMartyr.setOnAction(e -> updateMartyr());
		searchMartyr.setOnAction(e -> searchMartyr());

		pane.setTop(bar);

		/* Setting css style sheet for the scene */
		String css = getClass().getResource("style.css").toExternalForm();
		pane.getStylesheets().add(css);
		Scene scene = new Scene(pane, 1500, 700);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Martyrs of Palestine");
		stage.show();
	}

	public void searchLocation() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);

		Label lblLocName = new Label("Location name");
		TextField tfLocName = new TextField();
		Label lblDistName = new Label("District name");
		TextField tfDistName = new TextField();
		Button btClear = new Button("clear");
		Button btSearch = new Button("search");
		Label lblResult = new Label();
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btSearch);

		gPane.add(lblLocName, 0, 0);
		gPane.add(tfLocName, 1, 0);
		gPane.add(lblDistName, 0, 1);
		gPane.add(tfDistName, 1, 1);
		gPane.add(hbButtons, 1, 2);
		gPane.add(lblResult, 1, 5);
		pane.setCenter(gPane);

		Label lblTotalMart = new Label();
		Label lblTotalMale = new Label();
		Label lblTotalFemale = new Label();
		Label lblAvgAge = new Label();
		Label youngestLbl = new Label();
		Label oldestLbl = new Label();

		Label totalMart = new Label();
		Label totalMale = new Label();
		Label totalFemale = new Label();
		Label avgAge = new Label();
		Label lblYoungest = new Label();
		Label lblOldest = new Label();

		gPane.add(lblTotalMart, 0, 3);
		gPane.add(lblTotalMale, 1, 3);
		gPane.add(lblTotalFemale, 2, 3);
		gPane.add(lblAvgAge, 3, 3);
		gPane.add(youngestLbl, 4, 3);
		gPane.add(oldestLbl, 5, 3);

		gPane.add(totalMart, 0, 4);
		gPane.add(totalMale, 1, 4);
		gPane.add(totalFemale, 2, 4);
		gPane.add(avgAge, 3, 4);
		gPane.add(lblYoungest, 4, 4);
		gPane.add(lblOldest, 5, 4);

		GridPane.setHalignment(lblTotalMart, HPos.CENTER);
		GridPane.setHalignment(lblTotalMale, HPos.CENTER);
		GridPane.setHalignment(lblTotalFemale, HPos.CENTER);
		GridPane.setHalignment(lblAvgAge, HPos.CENTER);
		GridPane.setHalignment(youngestLbl, HPos.CENTER);
		GridPane.setHalignment(oldestLbl, HPos.CENTER);
		GridPane.setHalignment(totalMart, HPos.CENTER);
		GridPane.setHalignment(totalMale, HPos.CENTER);
		GridPane.setHalignment(totalFemale, HPos.CENTER);
		GridPane.setHalignment(avgAge, HPos.CENTER);
		GridPane.setHalignment(lblYoungest, HPos.CENTER);
		GridPane.setHalignment(lblOldest, HPos.CENTER);

		btClear.setOnAction(e -> {
			tfLocName.clear();
			tfDistName.clear();
			lblResult.setText("");
			lblTotalMart.setText("");
			lblTotalMale.setText("");
			lblTotalFemale.setText("");
			lblAvgAge.setText("");
			youngestLbl.setText("");
			oldestLbl.setText("");

			totalMart.setText("");
			totalMale.setText("");
			totalFemale.setText("");
			avgAge.setText("");
			lblYoungest.setText("");
			lblOldest.setText("");
		});

		btSearch.setOnAction(e -> {
			String districtName = tfDistName.getText();
			String locationName = tfLocName.getText();

			tfLocName.clear();
			tfDistName.clear();

			/* Check if the user kept any fields empty */
			if (districtName.isBlank() || locationName.isBlank()) {
				lblResult.setText("Fill all Fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* check if the District the user entered exists or not */
			int existDist = existsDistrict(districtName);
			if (existDist == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(existDist);

			/* check if the location the user entered exists in the district */
			int existLoc = dist.exists(locationName);

			if (existLoc == -1) {
				lblResult.setText("Location not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			Location loc = (Location) dist.getLocation().get(existLoc);

			lblResult.setText("");
			lblTotalMart.setText("Number of Martyrs");
			lblTotalMale.setText("Number of Males");
			lblTotalFemale.setText("Number of Females");
			lblAvgAge.setText("Average Age");
			youngestLbl.setText("Youngest Martyr");
			oldestLbl.setText("Oldest Martyr");

			/* Get the statistics */
			totalMart.setText(loc.getTotalNumOfMartyrs() + "");
			totalMale.setText(loc.getMales() + "");
			totalFemale.setText(loc.getFemales() + "");
			avgAge.setText(String.format("%.2f", loc.getAverageAge()));
			lblYoungest.setText(loc.youngest());
			lblOldest.setText(loc.oldest());

		});
	}

	public void searchMartyr() {
		pane.setBottom(null);
		Label lblDistrict = new Label("District");
		Label lblLocation = new Label("Location");
		Label lblName = new Label("name");
		Label lblResult = new Label();

		Label nameLabel = new Label();
		Label lblDate = new Label();
		Label lblAge = new Label();
		Label lblGender = new Label();

		Label lblMartName = new Label();
		Label lblMartDate = new Label();
		Label lblMartAge = new Label();
		Label lblMartGender = new Label();

		TextField tfDistrict = new TextField();
		TextField tfLocation = new TextField();
		TextField tfName = new TextField();

		Button btClear = new Button("clear");
		Button btSearch = new Button("search");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btSearch);

		GridPane gPane = new GridPane();
		pane.setCenter(gPane);
		gPane.add(lblDistrict, 0, 0);
		gPane.add(tfDistrict, 1, 0);
		gPane.add(lblLocation, 0, 1);
		gPane.add(tfLocation, 1, 1);
		gPane.add(lblName, 0, 2);
		gPane.add(tfName, 1, 2);
		gPane.add(hbButtons, 1, 3);
		gPane.add(lblResult, 1, 6);

		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);

		btClear.setOnAction(e -> {
			tfDistrict.clear();
			tfLocation.clear();
			tfName.clear();
			lblResult.setText("");
			nameLabel.setText("");
			lblDate.setText("");
			lblAge.setText("");
			lblGender.setText("");

			lblMartName.setText("");
			lblMartDate.setText("");
			lblMartAge.setText("");
			lblMartGender.setText("");
		});

		btSearch.setOnAction(e -> {
			String district = tfDistrict.getText();
			String location = tfLocation.getText();
			String name = tfName.getText();

			tfDistrict.clear();
			tfLocation.clear();
			tfName.clear();

			nameLabel.setText("");
			lblDate.setText("");
			lblAge.setText("");
			lblGender.setText("");

			lblMartName.setText("");
			lblMartDate.setText("");
			lblMartAge.setText("");
			lblMartGender.setText("");

			/* To check if the user left any black fields */
			if (district.isBlank() || location.isBlank() || name.isBlank()) {
				lblResult.setText("Fill all fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* To see if the district exists or not */
			int existsDist = existsDistrict(district);

			if (existsDist == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(existsDist);

			/* To search if the location exists or not */
			int existsLoc = dist.exists(location);

			if (existsLoc == -1) {
				lblResult.setText("Location not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			Location loc = (Location) dist.getLocation().get(existsLoc);

			/* Going through the martyrs in the entered location to get the martyr */
			for (int i = 0; i < loc.getMartyr().size(); i++) {
				Martyr mart = (Martyr) loc.getMartyr().get(i);
				if (mart.getName().contains(name) || mart.getName().equalsIgnoreCase(name)) {

					lblResult.setText("");

					nameLabel.setText("name");
					lblDate.setText("date");
					lblAge.setText("age");
					lblGender.setText("gender");

					gPane.add(nameLabel, 0, 4);
					gPane.add(lblDate, 1, 4);
					gPane.add(lblAge, 2, 4);
					gPane.add(lblGender, 3, 4);

					lblMartName.setText(mart.getName());
					lblMartDate.setText(mart.getDate());
					lblMartAge.setText(mart.getAge() + "");
					lblMartGender.setText(mart.getGender() + "");

					gPane.add(lblMartName, 0, 5);
					gPane.add(lblMartDate, 1, 5);
					gPane.add(lblMartAge, 2, 5);
					gPane.add(lblMartGender, 3, 5);

					GridPane.setHalignment(nameLabel, HPos.CENTER);
					GridPane.setHalignment(lblDate, HPos.CENTER);
					GridPane.setHalignment(lblAge, HPos.CENTER);
					GridPane.setHalignment(lblGender, HPos.CENTER);
					GridPane.setHalignment(lblMartName, HPos.CENTER);
					GridPane.setHalignment(lblMartDate, HPos.CENTER);
					GridPane.setHalignment(lblMartAge, HPos.CENTER);
					GridPane.setHalignment(lblMartGender, HPos.CENTER);
					return;
				}
			}
			lblResult.setText("Martyr not found");
			lblResult.setTextFill(Color.RED);
		});
	}

	public void updateMartyr() {
		pane.setBottom(null);
		Label lblResult = new Label();
		Label lblDistrict = new Label("District");
		TextField tfDistrict = new TextField();
		Label lblLocation = new Label("Location");
		TextField tfLocation = new TextField();
		Label lblOldName = new Label("old name");
		TextField tfOldName = new TextField();

		GridPane gPane = new GridPane();
		gPane.add(lblDistrict, 0, 0);
		gPane.add(tfDistrict, 1, 0);
		gPane.add(lblLocation, 0, 1);
		gPane.add(tfLocation, 1, 1);
		gPane.add(lblOldName, 0, 2);
		gPane.add(tfOldName, 1, 2);

		pane.setCenter(gPane);
		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);

		Label lblNewName = new Label("name");
		TextField tfNewName = new TextField();

		Label lblDate = new Label("date");

		Label lblDay = new Label("day");
		TextField tfDay = new TextField();
		Label lblMonth = new Label("month");
		TextField tfMonth = new TextField();
		Label lblYear = new Label("year");
		TextField tfYear = new TextField();
		HBox hbDate = new HBox(10);
		hbDate.getChildren().addAll(lblMonth, tfMonth, lblDay, tfDay, lblYear, tfYear);
		hbDate.setAlignment(Pos.CENTER);

		Label lblAge = new Label("age");
		TextField tfAge = new TextField();

		Label lblGedner = new Label("gender");
		RadioButton rdMale = new RadioButton("Male");
		RadioButton rdFemale = new RadioButton("Female");
		ToggleGroup tg = new ToggleGroup();
		rdMale.setToggleGroup(tg);
		rdFemale.setToggleGroup(tg);
		HBox hbRadio = new HBox(10);
		hbRadio.getChildren().addAll(rdMale, rdFemale);

		Button btClear = new Button("clear");
		Button btUpdate = new Button("update");
		HBox hbButton = new HBox(10);
		hbButton.getChildren().addAll(btClear, btUpdate);

		gPane.add(lblNewName, 0, 4);
		gPane.add(tfNewName, 1, 4);
		gPane.add(lblDate, 0, 5);
		gPane.add(hbDate, 1, 5);
		gPane.add(lblAge, 0, 6);
		gPane.add(tfAge, 1, 6);
		gPane.add(lblGedner, 0, 7);
		gPane.add(hbRadio, 1, 7);
		gPane.add(hbButton, 1, 8);
		gPane.add(lblResult, 1, 9);

		tfDay.setMaxWidth(70);
		tfMonth.setMaxWidth(70);
		tfYear.setMaxWidth(70);
		tfOldName.setMaxWidth(200);
		tfAge.setMaxWidth(200);
		tfLocation.setMaxWidth(200);
		tfDistrict.setMaxWidth(200);
		tfNewName.setMaxWidth(200);

		btClear.setOnAction(e -> {
			tfOldName.clear();
			tfNewName.clear();
			tfDay.clear();
			tfMonth.clear();
			tfYear.clear();
			tfAge.clear();
			tfLocation.clear();
			tfDistrict.clear();
			if (rdMale.isSelected() || rdFemale.isSelected())
				tg.getSelectedToggle().setSelected(false);
		});

		btUpdate.setOnAction(e -> {
			String oldName = tfOldName.getText();
			String newName = tfNewName.getText();
			String location = tfLocation.getText();
			String district = tfDistrict.getText();
			String age = tfAge.getText();
			String day = tfDay.getText();
			String month = tfMonth.getText();
			String year = tfYear.getText();

			/* To check if the user left the fields empty */
			if (oldName.isBlank() || newName.isBlank() || location.isBlank() || district.isBlank() || age.isBlank()
					|| day.isBlank() || month.isBlank() || year.isBlank()
					|| (!rdMale.isSelected() && !rdFemale.isSelected())) {
				lblResult.setText("Fill all fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* To check if the district entered exists or not */
			int existDis = existsDistrict(district);
			if (existDis == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(existDis);

			/*
			 * If the district does exist it checks if the location exists in this district
			 */
			int existLoc = dist.exists(location);
			if (existLoc == -1) {
				lblResult.setText("Location not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			Location loc = (Location) dist.getLocation().get(existLoc);

			/* It checks if the martyr exists and shows its information on the screen */
			for (int i = 0; i < loc.getMartyr().size(); i++) {
				Martyr mart = (Martyr) loc.getMartyr().get(i);
				if (oldName.equalsIgnoreCase(mart.getName())) {
					try {
						byte dayNum = Byte.parseByte(day);
						byte monthNum = Byte.parseByte(month);
						short yearNum = Short.parseShort(year);
						byte ageNum = Byte.parseByte(age);
						if (dayNum > 31 || dayNum < 1 || monthNum > 12 || monthNum < 1 || yearNum > 2024 || ageNum > 120
								|| ageNum < 0) {
							lblResult.setText("Invalid input");
							lblResult.setTextFill(Color.RED);
							return;
						}
						String date = monthNum + "/" + dayNum + "/" + yearNum;
						char newGender = 'M';
						if (rdMale.isSelected())
							newGender = 'M';
						else if (rdFemale.isSelected())
							newGender = 'F';

						Martyr newMart = new Martyr(newName, date, ageNum, location, district, newGender);

						loc.getMartyr().remove(i);

						addMartyr(newMart, loc);

						lblResult.setText("Record updated");
						lblResult.setTextFill(Color.WHITE);
						return;

					} catch (NumberFormatException ex) {
						lblResult.setText("Invalid Input");
						lblResult.setTextFill(Color.RED);
						return;
					}
				}
			}

			lblResult.setText("Martyr not found");

		});
	}

	public void deleteMartyr() {
		pane.setBottom(null);
		Label lblName = new Label("martyr name");
		TextField tfName = new TextField();
		Label lblDistrict = new Label("district name");
		TextField tfDistrict = new TextField();
		Label lblLocation = new Label("location name");
		TextField tfLocation = new TextField();

		Label lblResult = new Label();

		GridPane gPane = new GridPane();
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(lblDistrict, 0, 1);
		gPane.add(tfDistrict, 1, 1);
		gPane.add(lblLocation, 0, 2);
		gPane.add(tfLocation, 1, 2);
		gPane.add(lblResult, 1, 4);

		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);

		pane.setCenter(gPane);

		Button btClear = new Button("clear");
		Button btDelete = new Button("delete");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btDelete);

		gPane.add(hbButtons, 1, 3);

		btClear.setOnAction(e -> {
			tfName.clear();
			tfDistrict.clear();
			tfLocation.clear();
			lblResult.setText("");
		});

		btDelete.setOnAction(e -> {
			String name = tfName.getText();
			String location = tfLocation.getText();
			String district = tfDistrict.getText();

			/* To check if the user left the fields empty */
			if (name.isBlank() || location.isBlank() || district.isBlank()) {
				lblResult.setText("Fill all the fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* To check if the district entered exists or not */
			int existDistrict = existsDistrict(district);

			if (existDistrict == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(existDistrict);

			/*
			 * If the district does exist it checks if the location exists in this district
			 */
			int existLocation = dist.exists(location);

			if (existLocation == -1) {
				lblResult.setText("Location not found");
				lblResult.setTextFill(Color.RED);
				return;
			} else {

				Location loc = (Location) dist.getLocation().get(existLocation);

				Stage deleteStage = new Stage();
				Label warn = new Label("Are you sure you want to delete this record?");
				Button no = new Button("No");
				Button yes = new Button("Yes");
				HBox hbSure = new HBox(10);
				hbSure.getChildren().addAll(no, yes);
				hbSure.setAlignment(Pos.CENTER);
				GridPane gPane2 = new GridPane();
				gPane2.add(warn, 0, 0);
				gPane2.add(hbSure, 0, 1);
				gPane2.setAlignment(Pos.CENTER);

				Scene deleteScene = new Scene(gPane2, 300, 200);
				deleteStage.setScene(deleteScene);
				deleteStage.show();

				no.setOnAction(ev -> deleteStage.close());

				yes.setOnAction(ev -> {

					/* To search for the martyr and remove it from the list if it was found */
					for (int i = 0; i < loc.getMartyr().size(); i++) {
						Martyr mart = (Martyr) loc.getMartyr().get(i);
						if (name.equalsIgnoreCase(mart.getName())) {
							loc.getMartyr().remove(i);

							table.getItems().remove(mart);
							lblResult.setText("Martyr is removed");
							deleteStage.close();
							return;
						}
					}
					lblResult.setText("Martyr not found");
				});

			}
		});
	}

	public void addMartyrScene() {
		GridPane gPane = new GridPane();

		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);

		Button btClear = new Button("clear");
		Button btAdd = new Button("add");
		HBox hbAdd = new HBox(10);
		hbAdd.getChildren().addAll(btClear, btAdd);

		Label lblName = new Label("name");
		Label lblDate = new Label("martyr date");
		Label lblAge = new Label("age");
		Label lblLocation = new Label("location");
		Label lblDistrict = new Label("district");
		Label lblGender = new Label("gender");

		TextField tfName = new TextField();

		Label lblDay = new Label("day");
		TextField tfDay = new TextField();
		Label lblMonth = new Label("month");
		TextField tfMonth = new TextField();
		Label lblYear = new Label("year");
		TextField tfYear = new TextField();

		TextField tfAge = new TextField();
		TextField tfLocation = new TextField();
		TextField tfDistrict = new TextField();

		tfDay.setMaxWidth(70);
		tfMonth.setMaxWidth(70);
		tfYear.setMaxWidth(70);
		tfName.setMaxWidth(200);
		tfAge.setMaxWidth(200);
		tfLocation.setMaxWidth(200);
		tfDistrict.setMaxWidth(200);

		RadioButton rdMale = new RadioButton("Male");
		RadioButton rdFemale = new RadioButton("Female");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(rdMale, rdFemale);
		ToggleGroup tg = new ToggleGroup();
		rdMale.setToggleGroup(tg);
		rdFemale.setToggleGroup(tg);

		HBox hbDate = new HBox(10);
		hbDate.getChildren().addAll(lblMonth, tfMonth, lblDay, tfDay, lblYear, tfYear);
		hbDate.setAlignment(Pos.CENTER);

		gPane.add(lblName, 0, 0);
		gPane.add(lblDate, 0, 1);
		gPane.add(lblAge, 0, 2);
		gPane.add(lblLocation, 0, 3);
		gPane.add(lblDistrict, 0, 4);
		gPane.add(lblGender, 0, 5);

		gPane.add(tfName, 1, 0);
		gPane.add(hbDate, 1, 1);
		gPane.add(tfAge, 1, 2);
		gPane.add(tfLocation, 1, 3);
		gPane.add(tfDistrict, 1, 4);
		gPane.add(hbButtons, 1, 5);
		gPane.add(hbAdd, 1, 6);
		pane.setCenter(gPane);
		pane.setBottom(null);

		Label lblResult = new Label();

		gPane.add(lblResult, 1, 7);

		btClear.setOnAction(e -> {
			lblResult.setText("");
			tfName.clear();
			tfDay.clear();
			tfMonth.clear();
			tfYear.clear();
			tfAge.clear();
			tfLocation.clear();
			tfDistrict.clear();
			if (rdMale.isSelected() || rdFemale.isSelected())
				tg.getSelectedToggle().setSelected(false);

		});

		btAdd.setOnAction(e -> {
			String name = tfName.getText();
			String day = tfDay.getText();
			String month = tfMonth.getText();
			String year = tfYear.getText();
			String strAge = tfAge.getText();
			String location = tfLocation.getText();
			String district = tfDistrict.getText();

			/* To check if the user left the fields empty */
			if (name.isBlank() || day.isBlank() || month.isBlank() || year.isBlank() || strAge.isBlank()
					|| location.isBlank() || district.isBlank() || (!rdMale.isSelected() && !rdFemale.isSelected())) {
				lblResult.setText("Fill All fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			try {
				int numDay = Integer.parseInt(day);
				int numMonth = Integer.parseInt(month);
				int numYear = Integer.parseInt(year);
				byte age = Byte.parseByte(strAge);

				/* To check if the age is valid or not */
				if (age < 0 || age > 120)
					throw new NumberFormatException();

				/* To check if the dates are valid */
				if (numDay > 31 || numDay < 1 || numMonth > 12 || numMonth < 1 || numYear > 2024) {
					lblResult.setText("Invalid inputs");
					lblResult.setTextFill(Color.RED);
				} else {
					String date = month + "/" + day + "/" + year;
					char gender = 'M';
					if (rdMale.isSelected())
						gender = 'M';
					else if (rdFemale.isSelected())
						gender = 'F';

					/* Create new martyr object */
					Martyr mart = new Martyr(name, date, age, location, district, gender);
					/* To check if the district exists */
					int distExists = existsDistrict(district);
					if (distExists == -1) {
						lblResult.setText("District not found");
						lblResult.setTextFill(Color.RED);
						return;
					}

					District dist = (District) districts.get(distExists);

					/* To check if the location exists */
					int locExists = dist.exists(location);

					if (locExists == -1) {
						lblResult.setText("Location not found");
						lblResult.setTextFill(Color.RED);
						return;
					}

					Location loc = (Location) dist.getLocation().get(locExists);

					/* Add the martyr to the list */
					addMartyr(mart, loc);

					lblResult.setText(name + " is added successfully");

					tfName.clear();
					tfDay.clear();
					tfMonth.clear();
					tfYear.clear();
					tfAge.clear();
					tfLocation.clear();
					tfDistrict.clear();
					if (rdMale.isSelected() || rdFemale.isSelected())
						tg.getSelectedToggle().setSelected(false);

				}
			} catch (NumberFormatException ex) {
				lblResult.setText("Invalid inputs");
				lblResult.setTextFill(Color.RED);
			}
		});
	}

	public void navigateLocation() {
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);
		pane.setCenter(gPane);

		Button btNav = new Button("navigate");
		Label lblDistrict = new Label("district name");
		TextField tfDistrict = new TextField();
		Label lblWarning = new Label();

		Label lblName = new Label();
		Label lblTotalMart = new Label();
		Label lblAge = new Label();
		Label lblYoung = new Label();
		Label lblOld = new Label();
		Label locName = new Label();
		Label locTotalMart = new Label();
		Label locAge = new Label();
		Label locYoung = new Label();
		Label locOld = new Label();

		gPane.add(lblWarning, 3, 4);

		HBox hb = new HBox(5);
		hb.getChildren().addAll(lblDistrict, tfDistrict, btNav);
		hb.setAlignment(Pos.CENTER);

		pane.setBottom(hb);

		gPane.add(lblName, 0, 1);
		gPane.add(lblTotalMart, 1, 1);
		gPane.add(lblAge, 2, 1);
		gPane.add(lblYoung, 3, 1);
		gPane.add(lblOld, 4, 1);
		gPane.add(locName, 0, 2);
		gPane.add(locTotalMart, 1, 2);
		gPane.add(locAge, 2, 2);
		gPane.add(locYoung, 3, 2);
		gPane.add(locOld, 4, 2);

		tfDistrict.clear();

		GridPane.setHalignment(lblName, HPos.CENTER);
		GridPane.setHalignment(lblTotalMart, HPos.CENTER);
		GridPane.setHalignment(lblAge, HPos.CENTER);
		GridPane.setHalignment(lblYoung, HPos.CENTER);
		GridPane.setHalignment(lblOld, HPos.CENTER);
		GridPane.setHalignment(locName, HPos.CENTER);
		GridPane.setHalignment(locTotalMart, HPos.CENTER);
		GridPane.setHalignment(locAge, HPos.CENTER);
		GridPane.setHalignment(locYoung, HPos.CENTER);
		GridPane.setHalignment(locOld, HPos.CENTER);
		GridPane.setHalignment(hb, HPos.CENTER);

		Button btNext = new Button("next");
		hb.getChildren().add(btNext);

		btNav.setOnAction(e -> {
			String dist = tfDistrict.getText();

			/* check if the district the user entered exists or not */
			int exists = existsDistrict(dist);
			if (exists == -1) {
				lblWarning.setText("District not found");
				locName.setText("");
				locTotalMart.setText("");
				locAge.setText("");
				locYoung.setText("");
				locOld.setText("");
				lblName.setText("");
				lblTotalMart.setText("");
				lblAge.setText("");
				lblYoung.setText("");
				lblOld.setText("");
				lblWarning.setTextFill(Color.RED);
				return;
			}

			District district = (District) districts.get(exists);

			if (district.getLocation().size() == 0) {
				lblWarning.setText("No locations");
				locName.setText("");
				locTotalMart.setText("");
				locAge.setText("");
				locYoung.setText("");
				locOld.setText("");
				lblName.setText("");
				lblTotalMart.setText("");
				lblAge.setText("");
				lblYoung.setText("");
				lblOld.setText("");
				return;
			} else {
				lblWarning.setText("");
			}

			/* Getting the first node from the linked list */
			node = district.getLocation().getFront();
			Location loc = (Location) node.getElement();

			String name = loc.getName();

			lblName.setText("name");
			lblTotalMart.setText("total martyrs");
			lblAge.setText("average age");
			lblYoung.setText("younest");
			lblOld.setText("oldest");

			/* check if the martyrs list empty or not */
			if (loc.getMartyr().size() == 0) {
				locName.setText(name);
				locTotalMart.setText("0");
				locAge.setText("0");
				locYoung.setText("-");
				locOld.setText("-");
			} else {
				int totalMart = loc.getTotalNumOfMartyrs();
				double avgAge = loc.getAverageAge();
				String young = loc.youngest();
				String old = loc.oldest();
				locName.setText(name);
				locTotalMart.setText(totalMart + "");
				locAge.setText(avgAge + "");
				locYoung.setText(young);
				locOld.setText(old);
			}

			btNext.setOnAction(ev -> {
				/* If the user press next it will get the statistics of the next node */
				node = node.getNext();
				Location nextLocation = (Location) node.getElement();

				if (loc.getMartyr().size() == 0) {
					locName.setText(nextLocation.getName());
					locTotalMart.setText("0");
					locAge.setText("0");
					locYoung.setText("-");
					locOld.setText("-");
				} else {
					locName.setText(nextLocation.getName());
					locTotalMart.setText(nextLocation.getTotalNumOfMartyrs() + "");
					locAge.setText(String.format("%.2f", nextLocation.getAverageAge()));
					locYoung.setText(nextLocation.youngest());
					locOld.setText(nextLocation.oldest());
				}
			});

		});

	}

	public void updateLocation() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblDistrict = new Label("district name");
		TextField tfDistrict = new TextField();
		Label lblOldName = new Label("old location name");
		TextField tfOldName = new TextField();
		Label lblNewName = new Label("new location name");
		TextField tfNewName = new TextField();

		Button btUpdate = new Button("update");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btUpdate);
		hbButtons.setAlignment(Pos.CENTER);

		gPane.add(lblDistrict, 0, 0);
		gPane.add(tfDistrict, 1, 0);
		gPane.add(lblOldName, 0, 1);
		gPane.add(tfOldName, 1, 1);
		gPane.add(lblNewName, 0, 2);
		gPane.add(tfNewName, 1, 2);
		gPane.add(hbButtons, 1, 3);
		gPane.add(lblResult, 1, 4);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfDistrict.clear();
			tfOldName.clear();
			tfNewName.clear();
			lblResult.setText("");
		});

		btUpdate.setOnAction(e -> {
			/* Get the old and the new names the user entered */
			String districtName = tfDistrict.getText();
			String oldName = tfOldName.getText();
			String newName = tfNewName.getText();

			tfDistrict.clear();
			tfOldName.clear();
			tfNewName.clear();

			/* Check if the user entered a name or kept it null */
			if (oldName.isBlank() || newName.isBlank() || districtName.isBlank()) {
				lblResult.setText("No name was entered");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* Check if the district the user entered exists or not */
			int exists = existsDistrict(districtName);
			if (exists == -1) {
				lblResult.setText(districtName + " not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(exists);

			/*
			 * Check if the new district that the user want to insert already exists or not
			 */
			int newEx = dist.exists(newName);
			if (newEx != -1) {
				lblResult.setText(districtName + " already exists");
				lblResult.setTextFill(Color.RED);
				return;
			}

			int oldEx = dist.exists(oldName);
			if (oldEx == -1) {
				lblResult.setText(oldName + " not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			Location loc = (Location) dist.getLocation().get(oldEx);
			dist.getLocation().remove(oldEx);
			addLocation(newName, dist);

			for (int j = 0; j < loc.getMartyr().size(); j++) {
				table.getItems().remove(loc.getMartyr().get(j));

			}

			lblResult.setText("Location updated successfully");
			lblResult.setTextFill(Color.WHITE);
		});
	}

	public void deleteLocation() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblName = new Label("Location Name");
		TextField tfName = new TextField();
		Label lblDistrictName = new Label("District Name");
		TextField tfDistrictName = new TextField();
		Button btDelete = new Button("delete");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btDelete);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(lblDistrictName, 0, 1);
		gPane.add(tfDistrictName, 1, 1);
		gPane.add(hbButtons, 1, 2);
		gPane.add(lblResult, 1, 3);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfName.clear();
			tfDistrictName.clear();
			lblResult.setText("");
		});

		btDelete.setOnAction(e -> {
			String districtName = tfDistrictName.getText();
			String locatioName = tfName.getText();

			tfName.clear();
			tfDistrictName.clear();
			lblResult.setText("");

			if (districtName.isBlank() || locatioName.isBlank()) {
				lblResult.setText("Fill the fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			int exists = existsDistrict(districtName);
			if (exists == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District dist = (District) districts.get(exists);
			int locExists = dist.exists(locatioName);

			if (locExists == -1) {
				lblResult.setText("Location not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			Button no = new Button("no");
			Button yes = new Button("yes");
			HBox hbSure = new HBox(10);
			hbSure.getChildren().addAll(no, yes);
			hbSure.setAlignment(Pos.CENTER);
			Label lblSure = new Label("Are you sure you want to delete this location?");

			GridPane gPane2 = new GridPane();
			gPane2.add(lblSure, 0, 0);
			gPane2.add(hbSure, 0, 1);
			gPane2.setAlignment(Pos.CENTER);

			Scene scene2 = new Scene(gPane2, 300, 200);
			Stage stage2 = new Stage();
			stage2.setScene(scene2);
			stage2.show();

			no.setOnAction(ev -> stage2.close());
			yes.setOnAction(ev -> {
				Location loc = (Location) dist.getLocation().get(locExists);

				for (int i = 0; i < loc.getMartyr().size(); i++) {
					table.getItems().remove(loc.getMartyr().get(i));
				}

				dist.getLocation().remove(locExists);

				lblResult.setText("Location " + locatioName + " is deleted successfully");
				stage2.close();
			});

		});
	}

	public void addLocationScene() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblName = new Label("Location Name");
		TextField tfName = new TextField();
		Label lblDistrictName = new Label("District Name");
		TextField tfDistrictName = new TextField();
		Button btAdd = new Button("add");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btAdd);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(lblDistrictName, 0, 1);
		gPane.add(tfDistrictName, 1, 1);
		gPane.add(hbButtons, 1, 2);
		gPane.add(lblResult, 1, 3);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfName.clear();
			tfDistrictName.clear();
			lblResult.setText("");
		});

		btAdd.setOnAction(e -> {
			String locationName = tfName.getText();
			String districtName = tfDistrictName.getText();

			tfName.clear();
			tfDistrictName.clear();
			lblResult.setText("");

			if (locationName.isBlank() || districtName.isBlank()) {
				lblResult.setText("Fill the fields");
				lblResult.setTextFill(Color.RED);
				return;
			}

			int dist = existsDistrict(districtName);

			if (dist == -1) {
				lblResult.setText("District not found");
				lblResult.setTextFill(Color.RED);
				return;
			}

			District district = (District) districts.get(dist);
			int locIndex = district.exists(locationName);

			if (locIndex != -1) {
				lblResult.setText(locationName + " already exists");
				lblResult.setTextFill(Color.RED);
				return;
			}

			if (district.getLocation().size() == 0) {
				district.getLocation().addFirst(new SNode(new Location(locationName)));
				lblResult.setText(locationName + " is added successfully");
				lblResult.setTextFill(Color.WHITE);
				return;
			} else {
				addLocation(locationName, district);
				lblResult.setText(locationName + " is added successfully");
				lblResult.setTextFill(Color.WHITE);
			}

		});
	}

	public void printFile() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblName = new Label("Name");
		TextField tfName = new TextField();
		Button btPrint = new Button("print");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btPrint);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfName.clear();
			lblResult.setText("");
		});

		btPrint.setOnAction(e -> {
			String name = tfName.getText();

			tfName.clear();
			lblResult.setText("");

			if (name.isBlank()) {
				lblResult.setText("Fill the field");
				return;
			}

			int index = existsDistrict(name);

			if (index == -1) {
				lblResult.setText(name + " not found");
				return;
			}

			File newFile = new File(name + ".txt");
			try (PrintWriter print = new PrintWriter(newFile);) {
				District dist = (District) districts.get(index);

				print.println("Location: name, marts, males, females, avg ages");
				print.println();

				for (int i = 0; i < dist.getLocation().size(); i++) {
					Location loc = (Location) dist.getLocation().get(i);
					String locName = loc.getName();
					int total = loc.getTotalNumOfMartyrs();
					int males = loc.getMales();
					int females = loc.getFemales();
					double age = loc.getAverageAge();
					String line = "Location: " + locName + "," + total + "," + males + "," + females + ","
							+ String.format("%.2f", age);
					print.println(line);

					print.println();
					for (int j = 0; j < loc.getMartyr().size(); j++) {
						Martyr mart = (Martyr) loc.getMartyr().get(j);

						String martName = mart.getName();
						String martDate = mart.getDate();
						Byte martAge = mart.getAge();
						char martGender = mart.getGender();

						String martLine = martName + "," + martDate + "," + martAge + "," + martGender;
						print.println(martLine);

					}
					print.println();
				}

				lblResult.setText("District printed");

			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}

		});
	}

	public void dateMaxMart() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblRes = new Label();
		Label lblDay = new Label("day");
		TextField tfDay = new TextField();
		Label lblMonth = new Label("month");
		TextField tfMonth = new TextField();
		Label lblYear = new Label("year");
		TextField tfYear = new TextField();
		Button btClear = new Button("clear");
		Button btSearch = new Button("search");
		HBox hbDate = new HBox(10);
		hbDate.getChildren().addAll(lblMonth, tfMonth, lblDay, tfDay, lblYear, tfYear);
		hbDate.setAlignment(Pos.CENTER);
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btSearch);
		hbButtons.setAlignment(Pos.CENTER);

		tfDay.setMaxWidth(70);
		tfMonth.setMaxWidth(70);
		tfYear.setMaxWidth(70);

		gPane.add(hbDate, 0, 0);
		gPane.add(hbButtons, 0, 1);
		gPane.add(lblRes, 0, 2);
		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(10);
		gPane.setVgap(10);
		GridPane.setHalignment(lblRes, HPos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfDay.clear();
			tfMonth.clear();
			tfYear.clear();
			lblRes.setText("");
		});

		btSearch.setOnAction(e -> {
			String day = tfDay.getText();
			String month = tfMonth.getText();
			String year = tfYear.getText();

			tfDay.clear();
			tfMonth.clear();
			tfYear.clear();
			lblRes.setText("");

			if (day.isBlank() || month.isBlank() || year.isBlank()) {
				lblRes.setText("Fill all fields");
			} else if (day.length() > 2 || month.length() > 2 || year.length() != 4) {
				lblRes.setText("Invalid inputs");
			} else {
				try {
					int numDay = Integer.parseInt(day);
					int numMonth = Integer.parseInt(month);
					int numYear = Integer.parseInt(year);
					if (numDay > 31 || numDay < 1 || numMonth > 12 || numMonth < 1 || numYear > 2024)
						lblRes.setText("Invalid inputs");
					else {
						String date = month + "/" + day + "/" + year;
						int result = totalMartOfDate(date);
						lblRes.setText("The number of martyrs in " + date + " = " + result);
					}
				} catch (NumberFormatException ex) {
					lblRes.setText("Invalid inputs");
				}

			}
		});
	}

	public void navigateDistrict() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setHgap(20);
		gPane.setVgap(20);
		pane.setCenter(gPane);

		if (districts.size() == 0) {
			Label warning = new Label("No districts");
			warning.setTextFill(Color.RED);
			gPane.add(warning, 0, 0);
		} else {
			Button previous = new Button("previous");
			Button next = new Button("next");
			HBox hbButtons = new HBox(20);
			hbButtons.getChildren().addAll(previous, next);
			hbButtons.setAlignment(Pos.CENTER);
			Node node = districts.getFront();
			District dist = (District) node.getElement();

			Label lblName = new Label("Name");
			Label lblNumOfMarts = new Label("Number of Martyrs");
			Label lblMales = new Label("Number of Males");
			Label lblFemales = new Label("Number of Females");
			Label lblAges = new Label("Average Martyrs Ages");
			Label lblDates = new Label("Date with Highest Martyrs");

			Label name = new Label();
			Label numOfMarts = new Label();
			Label totalMales = new Label();
			Label totalFemales = new Label();
			Label ages = new Label();
			Label dates = new Label();

			gPane.add(lblName, 0, 0);
			gPane.add(lblNumOfMarts, 1, 0);
			gPane.add(lblMales, 2, 0);
			gPane.add(lblFemales, 3, 0);
			gPane.add(lblAges, 4, 0);
			gPane.add(lblDates, 5, 0);
			gPane.add(name, 0, 1);
			gPane.add(numOfMarts, 1, 1);
			gPane.add(totalMales, 2, 1);
			gPane.add(totalFemales, 3, 1);
			gPane.add(ages, 4, 1);
			gPane.add(dates, 5, 1);

			pane.setBottom(hbButtons);

			GridPane.setHalignment(lblName, HPos.CENTER);
			GridPane.setHalignment(lblNumOfMarts, HPos.CENTER);
			GridPane.setHalignment(lblMales, HPos.CENTER);
			GridPane.setHalignment(lblFemales, HPos.CENTER);
			GridPane.setHalignment(lblAges, HPos.CENTER);
			GridPane.setHalignment(lblDates, HPos.CENTER);
			GridPane.setHalignment(name, HPos.CENTER);
			GridPane.setHalignment(numOfMarts, HPos.CENTER);
			GridPane.setHalignment(totalMales, HPos.CENTER);
			GridPane.setHalignment(totalFemales, HPos.CENTER);
			GridPane.setHalignment(ages, HPos.CENTER);
			GridPane.setHalignment(dates, HPos.CENTER);
			GridPane.setHalignment(hbButtons, HPos.CENTER);

			if (dist.getLocation().size() == 0) {
				name.setText(dist.getName());
				numOfMarts.setText("0");
				totalMales.setText("0");
				totalFemales.setText("0");
				ages.setText("0");
				dates.setText("--");
			} else {

				name.setText(dist.getName());
				numOfMarts.setText(dist.getTotalNumOfMartyrs() + "");
				totalMales.setText(dist.getMales() + "");
				totalFemales.setText(dist.getFemales() + "");

				ages.setText(String.format("%.2f", dist.getAverageAge()));
				dates.setText(dist.getMaxDate());
			}

			distNode = node;

			/* Get the previous node */
			previous.setOnAction(e -> {
				if (distNode.getPrev() != null) {
					distNode = distNode.getPrev();
					District prevDist = (District) distNode.getElement();
					name.setText(prevDist.getName());
					numOfMarts.setText(prevDist.getTotalNumOfMartyrs() + "");
					totalMales.setText(prevDist.getMales() + "");
					totalFemales.setText(prevDist.getFemales() + "");
					ages.setText(String.format("%.2f", prevDist.getAverageAge()));
					dates.setText(prevDist.getMaxDate());
				}
			});

			/* Get the next node */
			next.setOnAction(e -> {
				if (distNode.getNext() != null) {

					distNode = distNode.getNext();
					District nextDist = (District) distNode.getElement();
					name.setText(nextDist.getName());
					numOfMarts.setText(nextDist.getTotalNumOfMartyrs() + "");
					totalMales.setText(nextDist.getMales() + "");
					totalFemales.setText(nextDist.getFemales() + "");
					ages.setText(String.format("%.2f", nextDist.getAverageAge()));
					dates.setText(nextDist.getMaxDate());
				}
			});
		}

	}

	public void updateDistrict() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblOldName = new Label("old name");
		TextField tfOldName = new TextField();
		Label lblNewName = new Label("new name");
		TextField tfNewName = new TextField();

		Button btUpdate = new Button("update");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btUpdate);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblOldName, 0, 0);
		gPane.add(tfOldName, 1, 0);
		gPane.add(lblNewName, 0, 1);
		gPane.add(tfNewName, 1, 1);
		gPane.add(hbButtons, 1, 2);
		gPane.add(lblResult, 1, 3);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfOldName.clear();
			tfNewName.clear();
			lblResult.setText("");
		});

		btUpdate.setOnAction(e -> {
			/* Get the old and the new names the user entered */
			String oldName = tfOldName.getText();
			String newName = tfNewName.getText();

			tfOldName.clear();
			tfNewName.clear();

			/* Check if the user entered a name or kept it null */
			if (oldName.isBlank() || newName.isBlank()) {
				lblResult.setText("No name was entered");
				lblResult.setTextFill(Color.RED);
				return;
			}

			int newExists = existsDistrict(newName);
			if (newExists != -1) {
				lblResult.setText(newName + " already exists");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/*
			 * Check if the name for the existing district is really exists to update with
			 * the new name
			 */
			int exists = existsDistrict(oldName);
			if (exists == -1) {
				lblResult.setText(oldName + " not found");
				lblResult.setTextFill(Color.RED);
			} else {
				District dist = (District) districts.get(exists);
				districts.remove(exists);
				// Add the district to list
				addDistrict(newName);

				for (int i = 0; i < dist.getLocation().size(); i++) {
					Location loc = (Location) dist.getLocation().get(i);
					for (int j = 0; j < loc.getMartyr().size(); j++) {
						table.getItems().remove(loc.getMartyr().get(j));
					}
				}

				lblResult.setText("District updated successfully");
				lblResult.setTextFill(Color.WHITE);
			}
		});
	}

	public void deleteDistrict() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblName = new Label("Name");
		TextField tfName = new TextField();
		Button btDelete = new Button("delete");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btDelete);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfName.clear();
			lblResult.setText("");
		});

		btDelete.setOnAction(e -> {
			/* To get the name the user enter */
			String name = tfName.getText();

			tfName.clear();

			/* Check if the user entered a name or kept it null */
			if (name.isBlank()) {
				lblResult.setText("No name was entered");
				lblResult.setTextFill(Color.RED);
				return;
			}

			/* Check if the name the user entered exists and return the position */
			int exists = existsDistrict(name);

			/* If the name is found and delete it */
			if (exists == -1) {
				lblResult.setText(name + " not found");
				lblResult.setTextFill(Color.RED);
			} else {
				Button btYes = new Button("yes");
				Button btCancel = new Button("cancel");
				HBox hb = new HBox(10);
				Label lblSure = new Label("Are you sure?");
				hb.getChildren().addAll(btCancel, btYes);
				GridPane gPane2 = new GridPane();
				gPane2.add(lblSure, 0, 0);
				gPane2.add(hb, 0, 1);
				Stage stage2 = new Stage();
				gPane2.setAlignment(Pos.CENTER);
				stage2.setScene(new Scene(gPane2, 300, 200));
				stage2.show();

				btYes.setOnAction(ev -> {

					District dist = (District) districts.get(exists);
					for (int i = 0; i < dist.getLocation().size(); i++) {
						Location loc = (Location) dist.getLocation().get(i);
						for (int j = 0; j < loc.getMartyr().size(); j++) {
							table.getItems().remove(loc.getMartyr().get(j));
						}
					}

					districts.remove(exists);

					lblResult.setText(name + " is deleted successfully");
					stage2.close();
				});

				btCancel.setOnAction(ev -> {
					stage2.close();
				});
			}
		});
	}

	public void addDistrictScene() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();
		Label lblResult = new Label();
		Label lblName = new Label("Name");
		TextField tfName = new TextField();
		Button btAdd = new Button("add");
		Button btClear = new Button("clear");
		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btAdd);
		hbButtons.setAlignment(Pos.CENTER);
		gPane.add(lblName, 0, 0);
		gPane.add(tfName, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);
		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfName.clear();
			lblResult.setText("");
		});

		btAdd.setOnAction(e -> {
			String name = tfName.getText();

			tfName.clear();
			/* To check if the district exists to delete it */
			int exists = existsDistrict(name);
			if (exists == -1) {
				addDistrict(name);
				lblResult.setText("District " + name + " Added Successfully");
			} else {
				lblResult.setText(name + " already exists");
			}
		});
	}

	/* Reading data from a file */
	public void read() {
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			while (in.hasNextLine()) {

				String line = in.nextLine();

				if (line == null)
					continue;
				String[] splitLine = line.split(",");
				String name = splitLine[0];
				String date = splitLine[1];
				byte age = 0;
				try {
					age = Byte.parseByte(splitLine[2]);
				} catch (NumberFormatException e) {
					age = 0;
				}
				String location = splitLine[3];
				String district = splitLine[4];
				char gender = splitLine[5].charAt(0);
				Martyr martyr = new Martyr(name, date, age, location, district, gender);

				add(martyr);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* To add a martyr */
	public void add(Martyr martyr) {
		String locationName = martyr.getLocation();
		String districtName = martyr.getDistrict();
		int dist = addDistrict(districtName);
		District district = (District) districts.get(dist);
		int loc = addLocation(locationName, district);
		Location location = (Location) district.getLocation().get(loc);
		addMartyr(martyr, location);
	}

	/* A method to add a district to the list if it does not exists */
	public int addDistrict(String districtName) {
		int index = existsDistrict(districtName);
		if (index == -1) {
			District newDistrict = new District(districtName);
			Node node = new Node(newDistrict);
			Node current = districts.getFront();
			for (int i = 0; i < districts.size(); i++) {
				District dist = (District) current.getElement();
				if (districtName.compareToIgnoreCase(dist.getName()) < 0) {
					districts.add(node, i);
					return i;
				}
				current = current.getNext();
			}
			districts.add(node);
			return districts.size() - 1;
		} else {
			return index;
		}
	}

	/* A method to add a location to a given district */
	public int addLocation(String locationName, District district) {
		int index = district.exists(locationName);
		if (index == -1) {
			Location newLocation = new Location(locationName);
			SNode node = new SNode(newLocation);
			SNode current = district.getLocation().getFront();
			for (int i = 0; i < district.getLocation().size(); i++) {
				Location loc = (Location) current.getElement();
				if (locationName.compareToIgnoreCase(loc.getName()) < 0) {
					district.getLocation().add(node, i);
					return i;
				}
				current = current.getNext();
			}
			district.getLocation().add(node);
			return district.getLocation().size() - 1;
		} else {
			return index;
		}
	}

	/* A method to add a martyr to a given location */
	public void addMartyr(Martyr martyr, Location location) {
		SNode node = new SNode(martyr);
		SNode current = location.getMartyr().getFront();
		if (current == null) {
			location.getMartyr().addFirst(node);
			table.getItems().add(martyr);
			return;
		}
		for (int i = 0; i < location.getMartyr().size(); i++) {
			Martyr mart = (Martyr) current.getElement();
			if (martyr.getAge() <= mart.getAge()) {
				location.getMartyr().add(node, i);
				table.getItems().add(martyr);
				return;
			}
			current = current.getNext();
		}
		location.getMartyr().add(node);
		table.getItems().add(martyr);
	}

	/* A method to check if a given district exists or not */
	public int existsDistrict(String districtName) {
		Node node = districts.getFront();
		for (int i = 0; i < districts.size(); i++) {
			District dist = (District) node.getElement();
			if (districtName.equalsIgnoreCase(dist.getName()))
				return i;
			node = node.getNext();
		}
		return -1;
	}

	/* A method to check the total number of martyrs in a given date */
	public int totalMartOfDate(String date) {
		int res = 0;
		for (int i = 0; i < districts.size(); i++) {
			District dist = (District) districts.get(i);
			res += dist.numOfMarts(date);
		}
		return res;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
