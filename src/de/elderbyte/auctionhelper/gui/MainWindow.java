package de.elderbyte.auctionhelper.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import de.elderbyte.auctionhelper.DataActualizer;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;

public class MainWindow {
    protected Shell shell;
	private Text server;
	private Text itemId;
	private Table table;
	private Text charactername;
	private Composite alertBar;
	private Label alertBarText;
	private DataActualizer dataAct;
	private Label date;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
	try {
	    MainWindow window = new MainWindow();
	    window.open();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Open the window.
     */
    public void open() {
	Display display = Display.getDefault();
	createContent();
	shell.open();
	shell.layout();
	
	while (!shell.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }
    
    
    protected void createContent() {	
	MainWindow window = this;
	
	shell = new Shell();
	shell.setMinimumSize(new Point(500, 39));
	shell.setSize(845, 485);
	shell.setText("WoW Auction Helper");
	
	GridLayout gl_shell = new GridLayout(1, false);
	gl_shell.marginWidth = 0;
	gl_shell.marginHeight = 0;
	shell.setLayout(gl_shell);
	
	alertBar = new Composite(shell, SWT.BORDER);
	alertBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	RowLayout rl_alertBar = new RowLayout(SWT.HORIZONTAL);
	rl_alertBar.justify = true;
	alertBar.setLayout(rl_alertBar);
	alertBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	alertBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
	
	alertBarText = new Label(alertBar, SWT.NONE);
	alertBarText.setLayoutData(new RowData(600, SWT.DEFAULT));
	alertBarText.setAlignment(SWT.CENTER);
	alertBarText.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.NORMAL));
	alertBarText.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	alertBarText.setText("Bitte einen Server w\u00E4hlen");
	
	Composite composite = new Composite(shell, SWT.NONE);
	composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	GridLayout gl_composite = new GridLayout(2, false);
	gl_composite.verticalSpacing = 0;
	gl_composite.marginWidth = 0;
	gl_composite.marginHeight = 0;
	composite.setLayout(gl_composite);
	
	Composite composite_5 = new Composite(composite, SWT.NONE);
	composite_5.setLayout(new RowLayout(SWT.HORIZONTAL));
	
	Composite composite_1 = new Composite(composite_5, SWT.NONE);
	RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
	rl_composite_1.center = true;
	composite_1.setLayout(rl_composite_1);
	
	Label text = new Label(composite_1, SWT.NONE);
	text.setText(" Server");
	
	server = new Text(composite_1, SWT.BORDER);
	server.setLayoutData(new RowData(100, SWT.DEFAULT));
	
	Composite composite_3 = new Composite(composite_5, SWT.NONE);
	RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
	rl_composite_3.center = true;
	composite_3.setLayout(rl_composite_3);
	
	Label lblCharaktername = new Label(composite_3, SWT.NONE);
	lblCharaktername.setText("Charaktername");
	
	charactername = new Text(composite_3, SWT.BORDER);
	charactername.setLayoutData(new RowData(100, SWT.DEFAULT));
	
	Composite composite_2 = new Composite(composite_5, SWT.NONE);
	RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
	rl_composite_2.center = true;
	composite_2.setLayout(rl_composite_2);
	
	Label lblItemId = new Label(composite_2, SWT.NONE);
	lblItemId.setText("Item ID");
	
	itemId = new Text(composite_2, SWT.BORDER);
	itemId.setLayoutData(new RowData(100, SWT.DEFAULT));
	
	Composite ok = new Composite(composite_5, SWT.NONE);
	
	Button btnNewButton = new Button(ok, SWT.NONE);
	btnNewButton.setSize(75, 25);
	btnNewButton.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
		    if(getDataAct() != null)
		        getDataAct().stop();
		    DataActualizer dataAct = new DataActualizer(window);
		    setDataAct(dataAct);
		    Display.getDefault().asyncExec(dataAct);
		}
	});
	btnNewButton.setText("OK");
	
	Composite composite_4 = new Composite(composite, SWT.NONE);
	composite_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
	RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
	rl_composite_4.marginRight = 8;
	composite_4.setLayout(rl_composite_4);
	
	Label lblLastActualized = new Label(composite_4, SWT.NONE);
	lblLastActualized.setText("Aktualisiert:");
	
	date = new Label(composite_4, SWT.NONE);
	date.setAlignment(SWT.RIGHT);
	date.setText("01.01.1970 00:00:00");
	
	ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	scrolledComposite.setExpandHorizontal(true);
	scrolledComposite.setExpandVertical(true);
	
	table = new Table(scrolledComposite, SWT.HIDE_SELECTION);
	table.setSize(721, 332);
	table.setLinesVisible(true);
	table.setHeaderVisible(true);
	
	TableColumn col_itemname = new TableColumn(table, SWT.NONE);
	col_itemname.setWidth(200);
	col_itemname.setText("Itemname");
	
	TableColumn col_charactername = new TableColumn(table, SWT.NONE);
	col_charactername.setWidth(200);
	col_charactername.setText("Charaktername");
	
	TableColumn col_auclength = new TableColumn(table, SWT.NONE);
	col_auclength.setWidth(100);
	col_auclength.setText("Auktionsl\u00E4nge");
	
	TableColumn tblclmnAnzahl = new TableColumn(table, SWT.NONE);
	tblclmnAnzahl.setWidth(100);
	tblclmnAnzahl.setText("Anzahl");
	
	TableColumn col_buyout = new TableColumn(table, SWT.NONE);
	col_buyout.setWidth(100);
	col_buyout.setText("Sofortkaufpreis");
	
	TableColumn col_difference = new TableColumn(table, SWT.NONE);
	col_difference.setWidth(100);
	col_difference.setText("Differenz");
	scrolledComposite.setContent(table);
	scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	
	
    }

    public Text getServer() {
        return server;
    }

    public void setServer(Text server) {
        this.server = server;
    }

    public Text getItemId() {
        return itemId;
    }

    public void setItemId(Text itemId) {
        this.itemId = itemId;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Text getCharactername() {
        return charactername;
    }

    public void setCharactername(Text charactername) {
        this.charactername = charactername;
    }

    public Shell getShell() {
        return shell;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public Composite getAlertBar() {
        return alertBar;
    }

    public void setAlertBar(Composite alertBar) {
        this.alertBar = alertBar;
    }

    public Label getAlertBarText() {
        return alertBarText;
    }

    public void setAlertBarText(Label alertBarText) {
        this.alertBarText = alertBarText;
    }

    public DataActualizer getDataAct() {
        return dataAct;
    }

    public void setDataAct(DataActualizer dataAct) {
        this.dataAct = dataAct;
    }

    public Label getDate() {
        return date;
    }

    public void setDate(Label date) {
        this.date = date;
    }
    
    
}
