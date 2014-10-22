/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import static editeurpanovisu.EditeurPanovisu.mniAffichagePlan;
import static editeurpanovisu.EditeurPanovisu.mniAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.copieFichierRepertoire;
import static editeurpanovisu.EditeurPanovisu.strCurrentDir;
import static editeurpanovisu.EditeurPanovisu.gestionnairePlan;
import static editeurpanovisu.EditeurPanovisu.ivAjouterPlan;
import static editeurpanovisu.EditeurPanovisu.iNombrePanoramiques;
import static editeurpanovisu.EditeurPanovisu.bDejaSauve;
import static editeurpanovisu.EditeurPanovisu.iNombrePanoramiquesFichier;
import static editeurpanovisu.EditeurPanovisu.iNombrePlans;
import static editeurpanovisu.EditeurPanovisu.panoramiquesProjet;
import static editeurpanovisu.EditeurPanovisu.plans;
import static editeurpanovisu.EditeurPanovisu.stPrincipal;
import static editeurpanovisu.EditeurPanovisu.strRepertAppli;
import static editeurpanovisu.EditeurPanovisu.strRepertTemp;
import static editeurpanovisu.EditeurPanovisu.tabPlan;
import static editeurpanovisu.EditeurPanovisu.strTooltipStyle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 * Gestion de l'interface de visualition de la visite virtuelle
 *
 * @author LANG Laurent
 */
public class GestionnaireInterfaceController {

    private final ExtensionsFilter IMAGE_FILTER
            = new ExtensionsFilter(new String[]{".png", ".jpg", ".bmp"});
    private final ExtensionsFilter PNG_FILTER
            = new ExtensionsFilter(new String[]{".png"});

    private ImageFond[] imagesFond = new ImageFond[50];
    private int iNombreImagesFond = 0;

    private String strStyleHotSpots = "hotspot.png";
    private String strStyleHotSpotImages = "photo2.png";
    private ResourceBundle rbLocalisation;
    /*
     Variables barre de navigation classique 
     */
    private double offsetXBarreClassique = 0;
    private double offsetYBarreClassique = 10;
    private double tailleBarreClassique = 26;
    private double espacementBarreClassique = 4;
    private final String strStyleDefautBarreClassique = "retina";
    private String strPositionBarreClassique = "bottom:center";
    private String styleBarreClassique = strStyleDefautBarreClassique;
    private String strDeplacementsBarreClassique = "oui";
    private String strZoomBarreClassique = "oui";
    private String strOutilsBarreClassique = "oui";
    private String strRotationBarreClassique = "oui";
    private String strPleinEcranBarreClassique = "oui";
    private String strSourisBarreClassique = "oui";
    private String strVisibiliteBarreClassique = "oui";
    private ComboBox cblisteStyleBarreClassique;
    private RadioButton rbTopLeftBarreClassique;
    private RadioButton rbTopCenterBarreClassique;
    private RadioButton rbTopRightBarreClassique;
    private RadioButton rbMiddleLeftBarreClassique;
    private RadioButton rbMiddleRightBarreClassique;
    private RadioButton rbBottomLeftBarreClassique;
    private RadioButton rbBottomCenterBarreClassique;
    private RadioButton rbBottomRightBarreClassique;
    private CheckBox cbBarreClassiqueVisible;
    private CheckBox cbDeplacementsBarreClassique;
    private CheckBox cbZoomBarreClassique;
    private CheckBox cbOutilsBarreClassique;
    private CheckBox cbFSBarreClassique;
    private CheckBox cbSourisBarreClassique;
    private CheckBox cbRotationBarreClassique;
    private Slider slEspacementBarreClassique;
    private BigDecimalField bdfOffsetXBarreClassique;
    private BigDecimalField bdfOffsetYBarreClassique;
    private ColorPicker cpCouleurBarreClassique;
    private Color couleurBarreClassique = Color.hsb(180, 0.39, 0.5);

    /*
     Variables barre de navigation classique 
     */
    private boolean bCouleurOrigineBarrePersonnalisee = true;
    private int iNombreZonesBarrePersonnalisee = 0;
    private double offsetXBarrePersonnalisee = 0;
    private double offsetYBarrePersonnalisee = 0;
    private double tailleBarrePersonnalisee = 100;
    private double tailleIconesBarrePersonnalisee = 40;
    private String strPositionBarrePersonnalisee = "bottom:right";
    private String strDeplacementsBarrePersonnalisee = "oui";
    private String strZoomBarrePersonnalisee = "oui";
    private String strInfoBarrePersonnalisee = "oui";
    private String strAideBarrePersonnalisee = "oui";
    private String strRotationBarrePersonnalisee = "oui";
    private String strPleinEcranBarrePersonnalisee = "oui";
    private String strSourisBarrePersonnalisee = "oui";
    private String strVisibiliteBarrePersonnalisee = "non";
    private String strLienImageBarrePersonnalisee = "";
    private String strLien1BarrePersonnalisee = "";
    private String strLien2BarrePersonnalisee = "";
    private ZoneTelecommande[] zonesBarrePersonnalisee = new ZoneTelecommande[50];
    private RadioButton rbTopLeftBarrePersonnalisee;
    private RadioButton rbTopCenterBarrePersonnalisee;
    private RadioButton rbTopRightBarrePersonnalisee;
    private RadioButton rbMiddleLeftBarrePersonnalisee;
    private RadioButton rbMiddleRightBarrePersonnalisee;
    private RadioButton rbBottomLeftBarrePersonnalisee;
    private RadioButton rbBottomCenterBarrePersonnalisee;
    private RadioButton rbBottomRightBarrePersonnalisee;
    private RadioButton rbCouleurOrigineBarrePersonnalisee;
    private RadioButton rbCouleurPersBarrePersonnalisee;
    private ImageView ivBarrePersonnalisee;
    private Image imgPngBarrePersonnalisee;
    private WritableImage wiBarrePersonnaliseeCouleur;
    private AnchorPane apAfficheBarrePersonnalisee;
    private CheckBox cbBarrePersonnaliseeVisible;
    private CheckBox cbDeplacementsBarrePersonnalisee;
    private CheckBox cbZoomBarrePersonnalisee;
    private CheckBox cbFSBarrePersonnalisee;
    private CheckBox cbSourisBarrePersonnalisee;
    private CheckBox cbRotationBarrePersonnalisee;
    private TextField tfLienImageBarrePersonnalisee;
    private TextField tfLien1BarrePersonnalisee;
    private TextField tfLien2BarrePersonnalisee;
    private Slider sltailleBarrePersonnalisee;
    private Slider sltailleIconesBarrePersonnalisee;
    private BigDecimalField bdfOffsetXBarrePersonnalisee;
    private BigDecimalField bdfOffsetYBarrePersonnalisee;
    private ColorPicker cpCouleurBarrePersonnalisee;
    private Color couleurBarrePersonnalisee = Color.hsb(180, 0.39, 0.5);
    final ToggleGroup grpPositionBarrePersonnalisee = new ToggleGroup();
    final ToggleGroup grpCouleurBarrePersonnalisee = new ToggleGroup();
    private Button btnEditerBarre;

    private boolean bAfficheTitre = true;
    private String strTitrePoliceNom = "Verdana";
    private String strTitrePoliceStyle = "Regular";
    private String strTitrePoliceTaille = "12.0";
    private String strCouleurTitre = "#ffffff";
    private String strCouleurFondTitre = "#000000";
    private double titreOpacite = 0.8;
    private double titreTaille = 100.0;

    private String strCouleurDiaporama = "#000000";
    private double diaporamaOpacite = 0.8;
    private ColorPicker cpCouleurDiaporama;
    private Slider slOpaciteDiaporama;
    private boolean bVisualiseDiaporama = false;
    private AnchorPane apAfficheDiapo;
    private ImageView ivDiapo;

    /**
     *
     */
    private boolean bAfficheBoussole = false;
    private String strImageBoussole = "rose3.png";
    private String strPositionBoussole = "top:right";
    private double offsetXBoussole = 20;
    private double offsetYBoussole = 20;
    private double tailleBoussole = 100;
    private double opaciteBoussole = 0.8;
    private boolean bAiguilleMobileBoussole = true;
    private String strRepertImagesFond = "";
    private String strRepertBarrePersonnalisee;
    private ImageView imgBoussole;
    private ImageView imgAiguille;
    private BigDecimalField bdfOffsetXBoussole;
    private BigDecimalField bdfOffsetYBoussole;
    private Slider slTailleBoussole;
    private Slider slOpaciteBoussole;
    private CheckBox cbAiguilleMobile;
    private CheckBox cbAfficheBoussole;
    private RadioButton rbBoussTopLeft;
    private RadioButton rbBoussTopRight;
    private RadioButton rbBoussBottomLeft;
    private RadioButton rbBoussBottomRight;

    /**
     * Variables Fenetre Info/Aide
     */
    private boolean bAfficheFenetreInfo = false;
    private boolean bAfficheFenetreAide = false;
    private boolean bFenetreInfoPersonnalise = false;
    private boolean bFenetreAidePersonnalise = false;
    private double fenetreInfoTaille = 100.d;
    private double fenetreAideTaille = 100.d;
    private double fenetreInfoPosX = 0.d;
    private double fenetreInfoPosY = 0.d;
    private double fenetreAidePosX = 0.d;
    private double fenetreAidePosY = 0.d;
    private double fenetreInfoposX = 0.d;
    private double fenetreInfoOpacite = 1.d;
    private double fenetreAideOpacite = 1.d;
    private double fenetrePoliceTaille = 10.d;
    private double fenetreURLPosX = 0.d;
    private double fenetreURLPosY = 0.d;
    private double fenetreOpaciteFond = 0.d;
    private String strFenetreInfoImage = "";
    private String strFenetreAideImage = "";
    private String strFenetreURL = "";
    private String strFenetreTexteURL = "";
    private String strFenetreURLInfobulle = "";
    private String strFenetreURLCouleur = "#FFFF00";
    private String strFenetrePolice = "Verdana";
    private String strFenetreCouleurFond = "#ffffff";

    private CheckBox cbFenetreInfoPersonnalise;
    private CheckBox cbFenetreAidePersonnalise;
    private TextField tfFenetreInfoImage;
    private TextField tfFenetreAideImage;
    private Slider slFenetreInfoTaille;
    private Slider slFenetreAideTaille;
    private BigDecimalField bdfFenetreInfoPosX;
    private BigDecimalField bdfFenetreInfoPosY;
    private BigDecimalField bdfFenetreAidePosX;
    private BigDecimalField bdfFenetreAidePosY;
    private Slider slFenetreInfoOpacite;
    private Slider slFenetreAideOpacite;
    private TextField tfFenetreTexteURL;
    private TextField tfFenetreURL;
    private TextField tfFenetreURLInfobulle;
    private ComboBox tfFenetrePolice;
    private Slider slFenetrePoliceTaille;
    private BigDecimalField bdfFenetreURLPosX;
    private BigDecimalField bdfFenetreURLPosY;
    private ColorPicker cpFenetreCouleurFond;
    private ColorPicker cpFenetreURLCouleur;
    private Slider slFenetreOpaciteFond;

    private final AnchorPane apFenetreAfficheInfo = new AnchorPane();
    private final Label lblFenetreURL = new Label();
    /**
     *
     */
    private String strRepertMasques;
    private boolean bAfficheMasque = false;
    private String strImageMasque = "MA.png";
    private String strPositionMasque = "top:right";
    private double dXMasque = 20;
    private double dYMasque = 20;
    private double tailleMasque = 30;
    private double opaciteMasque = 0.8;
    private boolean bMasqueNavigation = true;
    private boolean bMasqueBoussole = true;
    private boolean bMasqueTitre = true;
    private boolean bMasquePlan = true;
    private boolean bMasqueReseaux = true;
    private boolean bMasqueVignettes = true;
    private boolean bMasqueCombo = true;
    private boolean bMasqueSuivPrec = true;
    private boolean bMasqueHotspots = true;
    private ImageView ivMasque;
    private BigDecimalField bdfOffsetXMasque;
    private BigDecimalField bdfOffsetYMasque;
    private Slider slTailleMasque;
    private Slider slOpaciteMasque;
    private CheckBox cbAfficheMasque;
    private CheckBox cbMasqueNavigation;
    private CheckBox cbMasqueBoussole;
    private CheckBox cbMasqueTitre;
    private CheckBox cbMasquePlan;
    private CheckBox cbMasqueReseaux;
    private CheckBox cbMasqueVignettes;
    private CheckBox cbMasqueCombo;
    private CheckBox cbMasqueSuivPrec;
    private CheckBox cbMasqueHotspots;
    private RadioButton rbMasqueTopLeft;
    private RadioButton rbMasqueTopRight;
    private RadioButton rbMasqueBottomLeft;
    private RadioButton rbMasqueBottomRight;

    /**
     *
     */
    private String strRepertReseauxSociaux;
    private boolean bAfficheReseauxSociaux = false;
    private String strImageReseauxSociauxTwitter = "twitter.png";
    private String strImageReseauxSociauxGoogle = "google.png";
    private String strImageReseauxSociauxFacebook = "facebook.png";
    private String strImageReseauxSociauxEmail = "email.png";
    private String strPositionReseauxSociaux = "top:right";
    private double dXReseauxSociaux = 20;
    private double dYReseauxSociaux = 20;
    private double tailleReseauxSociaux = 30;
    private double opaciteReseauxSociaux = 0.8;
    private boolean bReseauxSociauxTwitter = true;
    private boolean bReseauxSociauxGoogle = true;
    private boolean bReseauxSociauxFacebook = true;
    private boolean bReseauxSociauxEmail = true;
    private ImageView ivTwitter;
    private ImageView ivGoogle;
    private ImageView ivFacebook;
    private ImageView ivEmail;
    private BigDecimalField bdfOffsetXReseauxSociaux;
    private BigDecimalField bdfOffsetYreseauxSociaux;
    private Slider slTailleReseauxSociaux;
    private Slider slOpaciteReseauxSociaux;
    private CheckBox cbAfficheReseauxSociaux;
    private CheckBox cbReseauxSociauxTwitter;
    private CheckBox cbReseauxSociauxGoogle;
    private CheckBox cbReseauxSociauxFacebook;
    private CheckBox cbReseauxSociauxEmail;
    private RadioButton rbReseauxSociauxTopLeft;
    private RadioButton rbReseauxSociauxTopRight;
    private RadioButton rbReseauxSociauxBottomLeft;
    private RadioButton rbReseauxSociauxBottomRight;
    /*
     *   variables Vignettes
     */
    private AnchorPane apVignettes;
    private AnchorPane apVisuVignettes;
    private boolean bAfficheVignettes = false;
    private String strCouleurFondVignettes = "#ffffff";
    private String strCouleurTexteVignettes = "#000000";
    private String strPositionVignettes = "bottom";
    private double tailleImageVignettes = 120;
    private double opaciteVignettes = 0.8;
    private Slider slOpaciteVignettes;
    private Slider slTailleVignettes;
    private CheckBox cbAfficheVignettes;
    private RadioButton rbVignettesLeft;
    private RadioButton rbVignettesRight;
    private RadioButton rbVignettesBottom;
    private ColorPicker cpCouleurFondVignettes;
    private ColorPicker cpCouleurTexteVignettes;
    /*
     *   variables ComboMenu
     */
    private AnchorPane apComboMenu;
    private AnchorPane apVisuComboMenu;
    private boolean bAfficheComboMenu = false;
    private boolean bAfficheComboMenuImages = true;
    private String strPositionXComboMenu = "left";
    private String strPositionYComboMenu = "top";
    private double offsetXComboMenu = 10;
    private double offsetYComboMenu = 10;
    private CheckBox cbAfficheComboMenu;
    private CheckBox cbAfficheComboMenuImages;
    private BigDecimalField bdfOffsetXComboMenu;
    private BigDecimalField bdfOffsetYComboMenu;
    private RadioButton rbComboMenuTopLeft;
    private RadioButton rbComboMenuTopCenter;
    private RadioButton rbComboMenuTopRight;
    private RadioButton rbComboMenuBottomLeft;
    private RadioButton rbComboMenuBottomCenter;
    private RadioButton rbComboMenuBottomRight;

    /*
     Variable du plan
     */
    private AnchorPane apPlan;
    private AnchorPane apVisuPlan;
    private boolean bAffichePlan = false;
    private String strPositionPlan = "left";
    private double largeurPlan = 200;
    private Color couleurFondPlan = Color.hsb(180, 0.39, 0.5);
    private String strCouleurFondPlan = couleurFondPlan.toString().substring(2, 8);
    private double opacitePlan = 0.8;
    private Color couleurTextePlan = Color.rgb(255, 255, 255);
    private String strCouleurTextePlan = couleurTextePlan.toString().substring(2, 8);
    private boolean bAfficheRadar = false;
    private Color couleurLigneRadar = Color.rgb(255, 255, 0);
    private String strCouleurLigneRadar = couleurLigneRadar.toString().substring(2, 8);
    private Color couleurFondRadar = Color.rgb(200, 0, 0);
    private String strCouleurFondRadar = couleurFondRadar.toString().substring(2, 8);
    private double tailleRadar = 40;
    private double opaciteRadar = 0.8;

    /*
     Eléments de l'onglet plan
     */
    private CheckBox cbAffichePlan;
    private Slider slOpacitePlan;
    private RadioButton rbPlanLeft;
    private RadioButton rbPlanRight;
    private ColorPicker cpCouleurFondPlan;
    private ColorPicker cpCouleurTextePlan;
    private Slider slLargeurPlan;
    private CheckBox cbAfficheRadar;
    private ColorPicker cpCouleurFondRadar;
    private ColorPicker cpCouleurLigneRadar;
    private Slider slTailleRadar;
    private Slider slOpaciteRadar;
    /*
     Variables Images Fond
     */

    private AnchorPane apImageFond;
    private double taillePanelImageFond;

    /*
     Variable du MenuContextuel
     */
    private AnchorPane apMenuContextuel;
    private AnchorPane apVisuMenuContextuel;
    private boolean bAfficheMenuContextuel = false;
    private boolean bAffichePrecSuivMC = true;
    private boolean bAffichePlanetNormalMC = true;
    private boolean bAffichePersMC1 = false;
    private String strPersLib1 = "";
    private String strPersURL1 = "";
    private boolean bAffichePersMC2 = false;
    private String strPersLib2 = "";
    private String strPersURL2 = "";

    /*
     Eléments de l'onglet MenuContextuel
     */
    private CheckBox cbAfficheMenuContextuel;
    private CheckBox cbAffichePrecSuivMC;
    private CheckBox cbAffichePlanetNormalMC;
    private CheckBox cbAffichePersMC1;
    private CheckBox cbAffichePersMC2;
    private TextField tfPersLib1;
    private TextField tfPersURL1;
    private TextField tfPersLib2;
    private TextField tfPersURL2;

    public Pane paneTabInterface;
    private HBox hbInterface;
    private AnchorPane apVisualisation;
    private VBox vbOutils;
    private RadioButton rbClair;
    private RadioButton rbSombre;
    private RadioButton rbPerso;
    private ComboBox cbImage;
    private ImageView ivVisualisation;
    final ToggleGroup tgImage = new ToggleGroup();
    final ToggleGroup tgPositionBarreClassique = new ToggleGroup();
    final ToggleGroup tgPosBouss = new ToggleGroup();
    final ToggleGroup tgPosMasque = new ToggleGroup();
    final ToggleGroup tgPosReseauxSociaux = new ToggleGroup();
    final ToggleGroup tgPosVignettes = new ToggleGroup();
    final ToggleGroup tgPosComboMenu = new ToggleGroup();
    final ToggleGroup tgPosPlan = new ToggleGroup();
    private Image imgClaire;
    private Image imgSombre;
    private HBox hbbarreBoutons;
    private HBox hbOutils;
    private Label lblTxtTitre;
    private ImageView ivInfo;
    private ImageView ivAide;
    private ImageView ivAutoRotation;
    private ImageView ivModeSouris;
    private ImageView ivModeSouris2;
    private ImageView ivPleinEcran;
    private ImageView ivPleinEcran2;
    private HBox hbZoom;
    private ImageView ivZoomPlus;
    private ImageView ivZoomMoins;
    private HBox hbDeplacements;
    private ImageView ivHaut;
    private ImageView ivBas;
    private ImageView ivGauche;
    private ImageView ivDroite;
    private ImageView ivHotSpot;
    private ImageView ivHotSpotImage;

    private String strRepertBoutonsPrincipal;
    private String strRepertHotSpots;
    private String strRepertHotSpotsPhoto;
    private String strRepertBoussoles;
    private CheckBox cbSuivantPrecedent;
    private ImageView imgSuivant;
    private ImageView imgPrecedent;
    private VBox vbFondSuivant;
    private VBox vbFondPrecedent;
    private boolean bSuivantPrecedent;
    private CheckBox cbAfficheTitre;
    private ColorPicker cpCouleurFondTitre;
    private ColorPicker cpCouleurTitre;
    private ComboBox cbListePolices;
    private Slider slTaillePolice;
    private Slider slOpacite;
    private Slider slTaille;
    private ColorPicker cpCouleurTheme;
    private ColorPicker cpCouleurHotspots;
    private ColorPicker cpCouleurHotspotsPhoto;
    private ColorPicker cpCouleurMasques;
    private Color couleurHotspots = Color.hsb(180, 0.39, 0.5);
    private Color couleurHotspotsPhoto = Color.hsb(180, 0.39, 0.5);
    private Color couleurMasque = Color.hsb(180, 0.39, 0.5);
    private Color couleurTheme = Color.hsb(180, 0.39, 0.5);
    private Image[] imgBoutons = new Image[25];
    private String[] strNomImagesBoutons = new String[25];
    private PixelReader[] prLisBoutons = new PixelReader[25];
    private WritableImage[] wiNouveauxBoutons = new WritableImage[25];
    private PixelWriter[] pwNouveauxBoutons = new PixelWriter[25];
    private int iNombreImagesBouton = 0;
    private Image imgMasque;
    private PixelReader prLisMasque;
    private WritableImage wiNouveauxMasque;
    private PixelWriter pwNouveauxMasque;

    private void chargeBarre(String strStyleBarre, String strHotSpot, String strMA) {
        File fileRepertBarre = new File(strRepertBoutonsPrincipal + File.separator + strStyleBarre);
        File[] fileRepertoires = fileRepertBarre.listFiles(IMAGE_FILTER);
        int i = 0;
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomFich = fileRepert.getName();
                String strNomFichComplet = fileRepert.getAbsolutePath();
                getImgBoutons()[i] = new Image("file:" + strNomFichComplet);
                getPrLisBoutons()[i] = getImgBoutons()[i].getPixelReader();
                int iLargeur = (int) getImgBoutons()[i].getWidth();
                int iHauteur = (int) getImgBoutons()[i].getHeight();
                getWiNouveauxBoutons()[i] = new WritableImage(iLargeur, iHauteur);
                getPwNouveauxBoutons()[i] = getWiNouveauxBoutons()[i].getPixelWriter();
                switch (strNomFich) {
                    case "aide.png":
                        ivAide = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "info.png":
                        ivInfo = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "haut.png":
                        ivHaut = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "bas.png":
                        ivBas = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "droite.png":
                        ivDroite = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "gauche.png":
                        ivGauche = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "fs.png":
                        ivPleinEcran = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "fs2.png":
                        ivPleinEcran2 = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "rotation.png":
                        ivAutoRotation = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "souris.png":
                        ivModeSouris = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "souris2.png":
                        ivModeSouris2 = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "zoomin.png":
                        ivZoomPlus = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                    case "zoomout.png":
                        ivZoomMoins = new ImageView(getWiNouveauxBoutons()[i]);
                        break;
                }
                getStrNomImagesBoutons()[i] = strNomFich;
                i++;
            }
        }
        setiNombreImagesBouton(i);
        getImgBoutons()[getiNombreImagesBouton()] = new Image("file:" + strRepertHotSpots + File.separator + strHotSpot);
        getPrLisBoutons()[getiNombreImagesBouton()] = getImgBoutons()[getiNombreImagesBouton()].getPixelReader();
        int iLargeur = (int) getImgBoutons()[getiNombreImagesBouton()].getWidth();
        int iHauteur = (int) getImgBoutons()[getiNombreImagesBouton()].getHeight();
        getWiNouveauxBoutons()[getiNombreImagesBouton()] = new WritableImage(iLargeur, iHauteur);
        getPwNouveauxBoutons()[getiNombreImagesBouton()] = getWiNouveauxBoutons()[getiNombreImagesBouton()].getPixelWriter();
        ivHotSpot = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);
        setiNombreImagesBouton(i + 1);
        getImgBoutons()[getiNombreImagesBouton()] = new Image("file:" + strRepertHotSpotsPhoto + File.separator + getStrStyleHotSpotImages());
        getPrLisBoutons()[getiNombreImagesBouton()] = getImgBoutons()[getiNombreImagesBouton()].getPixelReader();
        iLargeur = (int) getImgBoutons()[getiNombreImagesBouton()].getWidth();
        iHauteur = (int) getImgBoutons()[getiNombreImagesBouton()].getHeight();
        getWiNouveauxBoutons()[getiNombreImagesBouton()] = new WritableImage(iLargeur, iHauteur);
        getPwNouveauxBoutons()[getiNombreImagesBouton()] = getWiNouveauxBoutons()[getiNombreImagesBouton()].getPixelWriter();
        ivHotSpotImage = new ImageView(getWiNouveauxBoutons()[getiNombreImagesBouton()]);

        setImgMasque(new Image("file:" + strRepertMasques + File.separator + strMA));

        setPrLisMasque(getImgMasque().getPixelReader());
        iLargeur = (int) getImgMasque().getWidth();
        iHauteur = (int) getImgMasque().getHeight();
        setWiNouveauxMasque(new WritableImage(iLargeur, iHauteur));
        setPwNouveauxMasque(getWiNouveauxMasque().getPixelWriter());
        ivMasque = new ImageView(getWiNouveauxMasque());

        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
    }

    private void changeCouleurBarrePersonnalisee(double couleurFinale, double sat, double bright) {
        PixelReader prBarrePersonnalisee;
        prBarrePersonnalisee = imgPngBarrePersonnalisee.getPixelReader();
        setWiBarrePersonnaliseeCouleur(new WritableImage((int) imgPngBarrePersonnalisee.getWidth(), (int) imgPngBarrePersonnalisee.getHeight()));
        PixelWriter pwBarrePersonnaliseeCouleur = getWiBarrePersonnaliseeCouleur().getPixelWriter();
        if (isbCouleurOrigineBarrePersonnalisee()) {
            for (int y = 0; y < imgPngBarrePersonnalisee.getHeight(); y++) {
                for (int x = 0; x < imgPngBarrePersonnalisee.getWidth(); x++) {
                    Color color = prBarrePersonnalisee.getColor(x, y);
                    double brightness = color.getBrightness();
                    double hue = color.getHue();  //getHue() return 0.0-360.0
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    couleur = Color.hsb(hue, sat, bright, opacity);
                    pwBarrePersonnaliseeCouleur.setColor(x, y, prBarrePersonnalisee.getColor(x, y));
                }
            }

        } else {
            for (int y = 0; y < imgPngBarrePersonnalisee.getHeight(); y++) {
                for (int x = 0; x < imgPngBarrePersonnalisee.getWidth(); x++) {
                    Color color = prBarrePersonnalisee.getColor(x, y);
                    double brightness = color.getBrightness();
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    double bright1;
                    double sat1;
                    if (sat < 0.05) {
                        couleur = Color.hsb(couleurFinale, sat, (brightness + bright) / 2.d, opacity);
                    } else {
                        if (brightness > 0.9 || brightness < 0.1) {
                            bright1 = brightness;
                        } else {
                            bright1 = (brightness * 2.d + bright) / 3.d;
                        }
                        if (saturation < 0.35) {
                            sat1 = saturation;
                        } else {
                            sat1 = (saturation + sat) / 2.d;
                        }
                        couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                    }
                    pwBarrePersonnaliseeCouleur.setColor(x, y, couleur);
                }
            }

        }

    }

    private void changeCouleurBarreClassique(double couleurFinale, double sat, double bright) {
        for (int i = 0; i < getiNombreImagesBouton() - 1; i++) {
            for (int y = 0; y < getImgBoutons()[i].getHeight(); y++) {
                for (int x = 0; x < getImgBoutons()[i].getWidth(); x++) {
                    Color color = getPrLisBoutons()[i].getColor(x, y);
                    double brightness = color.getBrightness();
                    //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                    double saturation = color.getSaturation();
                    double opacity = color.getOpacity();
                    Color couleur;
                    if (sat < 0.1) {
                        couleur = Color.hsb(couleurFinale, sat, bright, opacity);
                    } else {
                        if (saturation < 0.2) {
                            couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                        } else {
                            couleur = Color.hsb(couleurFinale, saturation * 0.4 + sat * 0.6, brightness * 0.4 + bright * 0.6, opacity);
                        }
                    }
                    getPwNouveauxBoutons()[i].setColor(x, y, couleur);
                }
            }
        }

    }

    private void changeCouleurMasque(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < getImgMasque().getHeight(); y++) {
            for (int x = 0; x < getImgMasque().getWidth(); x++) {
                Color color = getPrLisMasque().getColor(x, y);
                double brightness = color.getBrightness();
                //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    double bright1;
                    if (brightness > 0.1 && brightness < 0.9) {
                        bright1 = brightness * 0.5 + bright * 0.5;
                    } else {
                        bright1 = brightness;
                    }
                    couleur = Color.hsb(couleurFinale, sat, bright1, opacity);
                } else {
                    double sat1;
                    double bright1;
                    if (saturation < 0.3 || (brightness < 0.2 || brightness > 0.8)) {
                        sat1 = saturation;
                        bright1 = brightness;
                    } else {
                        sat1 = saturation * (1 - saturation + sat);
                        double exp = Math.exp(-4 * Math.pow(2 * brightness - 1, 2));
                        bright1 = bright * exp + brightness * (1 - exp);
                    }
//                            if (brightness > 0.1 && brightness < 0.9) {
//                        bright1 = brightness * 0.5 + bright * 0.5;
//                    } else {
//                        bright1 = brightness;
//                    }
                    couleur = Color.hsb(couleurFinale, sat1, bright1, opacity);

                }
                getPwNouveauxMasque().setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurHS(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < getImgBoutons()[getiNombreImagesBouton() - 1].getHeight(); y++) {
            for (int x = 0; x < getImgBoutons()[getiNombreImagesBouton() - 1].getWidth(); x++) {
                Color color = getPrLisBoutons()[getiNombreImagesBouton() - 1].getColor(x, y);
                double brightness = color.getBrightness();
                //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    couleur = Color.hsb(couleurFinale, sat, brightness * 0.5 + bright * 0.5, opacity);
                } else {
                    if (saturation > 0.05) {
                        couleur = Color.hsb(couleurFinale, saturation * 0.5 + sat * 0.5, brightness * 0.5 + bright * 0.5, opacity);
                    } else {
                        couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                    }
                }
                getPwNouveauxBoutons()[getiNombreImagesBouton() - 1].setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurHSPhoto(double couleurFinale, double sat, double bright) {
        for (int y = 0; y < getImgBoutons()[getiNombreImagesBouton()].getHeight(); y++) {
            for (int x = 0; x < getImgBoutons()[getiNombreImagesBouton()].getWidth(); x++) {
                Color color = getPrLisBoutons()[getiNombreImagesBouton()].getColor(x, y);
                double brightness = color.getBrightness();
                //double hue = color.getHue() / 360.0;  //getHue() return 0.0-360.0
                double saturation = color.getSaturation();
                double opacity = color.getOpacity();
                Color couleur;
                if ((sat < 0.02) && (saturation > 0.05)) {
                    couleur = Color.hsb(couleurFinale, sat, brightness * 0.5 + bright * 0.5, opacity);
                } else {
                    if (saturation > 0.05) {
                        couleur = Color.hsb(couleurFinale, saturation * 0.5 + sat * 0.5, brightness * 0.5 + bright * 0.5, opacity);
                    } else {
                        couleur = Color.hsb(couleurFinale, saturation, brightness, opacity);
                    }
                }
                getPwNouveauxBoutons()[getiNombreImagesBouton()].setColor(x, y, couleur);
            }
        }
    }

    private void changeCouleurTitre(String coul) {
        setStrCouleurFondTitre("#" + coul);
        lblTxtTitre.setStyle("-fx-background-color : " + getStrCouleurFondTitre());
    }

    private void changeCouleurVignettes(String coul) {
        setStrCouleurFondVignettes("#" + coul);
        afficheVignettes();
    }

    /**
     *
     */
    private void afficheBoussole() {
        imgAiguille.setVisible(isbAfficheBoussole());
        imgBoussole.setVisible(isbAfficheBoussole());

        imgAiguille.setFitWidth(getTailleBoussole() / 5);
        imgAiguille.setFitHeight(getTailleBoussole());
        imgAiguille.setOpacity(getOpaciteBoussole());
        imgAiguille.setSmooth(true);

        imgBoussole.setImage(new Image("file:" + strRepertBoussoles + File.separator + getStrImageBoussole()));
        imgBoussole.setFitWidth(getTailleBoussole());
        imgBoussole.setFitHeight(getTailleBoussole());
        imgBoussole.setOpacity(getOpaciteBoussole());
        imgBoussole.setSmooth(true);
        String strPositXBoussole = getStrPositionBoussole().split(":")[1];
        String strPositYBoussole = getStrPositionBoussole().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getOffsetXBoussole();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getOffsetYBoussole();
                break;
        }
        switch (getStrPositionVignettes()) {
            case "bottom":
                if (strPositYBoussole.equals("bottom")) {
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                }
                break;
            case "left":
                if (strPositXBoussole.equals("left")) {
                    posX = ivVisualisation.getLayoutX() + getOffsetXBoussole() + apVisuVignettes.getPrefWidth();
                }
                break;
            case "right":
                if (strPositXBoussole.equals("right")) {
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                }
                break;
        }
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);
        imgAiguille.setOpacity(getOpaciteBoussole());
        imgAiguille.setVisible(isbAfficheBoussole());

        imgBoussole.setOpacity(getOpaciteBoussole());
        imgBoussole.setVisible(isbAfficheBoussole());
    }

    private void afficheImage(int index) {
        Image imgAffiche = panoramiquesProjet[index].getImgPanoramique();
        Rectangle2D r2dVue = new Rectangle2D(200, 0, 800, 600);
        ivVisualisation.setViewport(r2dVue);
        ivVisualisation.setImage(imgAffiche);
    }

    /**
     *
     */
    private void afficheMasque() {
        apVisualisation.getChildren().remove(ivMasque);
        apVisualisation.getChildren().add(ivMasque);
        ivMasque.setVisible(isbAfficheMasque());
        ivMasque.setFitWidth(getTailleMasque());
        ivMasque.setFitHeight(getTailleMasque());
        ivMasque.setOpacity(getOpaciteMasque());
//        ivMasque.setSmooth(true);
        String strPositXMasque = getStrPositionMasque().split(":")[1];
        String strPositYMasque = getStrPositionMasque().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXMasque) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getdXMasque();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getdXMasque() - ivMasque.getFitWidth();
                break;
        }
        switch (strPositYMasque) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - ivMasque.getFitHeight() - getdYMasque();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getdYMasque();
                break;
        }
        ivMasque.setLayoutX(posX);
        ivMasque.setLayoutY(posY);
//
    }

    /**
     *
     */
    private void afficheReseauxSociaux() {
        ivTwitter.setVisible(isbAfficheReseauxSociaux());
        ivGoogle.setVisible(isbAfficheReseauxSociaux());
        ivFacebook.setVisible(isbAfficheReseauxSociaux());
        ivEmail.setVisible(isbAfficheReseauxSociaux());
        ivTwitter.setFitWidth(getTailleReseauxSociaux());
        ivTwitter.setFitHeight(getTailleReseauxSociaux());
        ivTwitter.setOpacity(getOpaciteReseauxSociaux());
        ivTwitter.setSmooth(true);
        ivTwitter.setVisible(false);
        ivGoogle.setFitWidth(getTailleReseauxSociaux());
        ivGoogle.setFitHeight(getTailleReseauxSociaux());
        ivGoogle.setOpacity(getOpaciteReseauxSociaux());
        ivGoogle.setSmooth(true);
        ivGoogle.setVisible(false);
        ivFacebook.setFitWidth(getTailleReseauxSociaux());
        ivFacebook.setFitHeight(getTailleReseauxSociaux());
        ivFacebook.setOpacity(getOpaciteReseauxSociaux());
        ivFacebook.setSmooth(true);
        ivFacebook.setVisible(false);
        ivEmail.setFitWidth(getTailleReseauxSociaux());
        ivEmail.setFitHeight(getTailleReseauxSociaux());
        ivEmail.setOpacity(getOpaciteReseauxSociaux());
        ivEmail.setSmooth(true);
        ivEmail.setVisible(false);
        String strPositXReseauxSociaux = getStrPositionReseauxSociaux().split(":")[1];
        String strPositYReseauxSociaux = getStrPositionReseauxSociaux().split(":")[0];
        double posX;
        double posY = 0;
        double dX;
        switch (strPositXReseauxSociaux) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getdXReseauxSociaux();
                dX = ivEmail.getFitWidth() + 5;
                if (isbReseauxSociauxTwitter() && isbAfficheReseauxSociaux()) {
                    ivTwitter.setLayoutX(posX);
                    ivTwitter.setVisible(true);
                    posX += dX;

                }
                if (isbReseauxSociauxGoogle() && isbAfficheReseauxSociaux()) {
                    ivGoogle.setLayoutX(posX);
                    ivGoogle.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxFacebook() && isbAfficheReseauxSociaux()) {
                    ivFacebook.setLayoutX(posX);
                    ivFacebook.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }

                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getdXReseauxSociaux() - ivEmail.getFitWidth();
                dX = -(ivEmail.getFitWidth() + 5);
                if (isbReseauxSociauxEmail() && isbAfficheReseauxSociaux()) {
                    ivEmail.setLayoutX(posX);
                    ivEmail.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxFacebook() && isbAfficheReseauxSociaux()) {
                    ivFacebook.setLayoutX(posX);
                    ivFacebook.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxGoogle() && isbAfficheReseauxSociaux()) {
                    ivGoogle.setLayoutX(posX);
                    ivGoogle.setVisible(true);
                    posX += dX;
                }
                if (isbReseauxSociauxTwitter() && isbAfficheReseauxSociaux()) {
                    ivTwitter.setLayoutX(posX);
                    ivTwitter.setVisible(true);
                    posX += dX;
                }
                break;
        }
        switch (strPositYReseauxSociaux) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - ivEmail.getFitHeight() - getdYReseauxSociaux();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getdYReseauxSociaux();
                break;
        }
        ivTwitter.setLayoutY(posY);
        ivGoogle.setLayoutY(posY);
        ivFacebook.setLayoutY(posY);
        ivEmail.setLayoutY(posY);
    }

    public void afficheDiaporama() {
        apAfficheDiapo.getChildren().clear();
        apAfficheDiapo.setOpacity(getDiaporamaOpacite());
        apAfficheDiapo.setStyle("-fx-background-color : " + getStrCouleurDiaporama());
        apAfficheDiapo.setVisible(bVisualiseDiaporama);
        ivDiapo.setVisible(bVisualiseDiaporama);
    }

    /**
     *
     */
    public void afficheFenetreInfo() {
        if (bAfficheFenetreInfo) {
            apFenetreAfficheInfo.setVisible(true);
            lblFenetreURL.setVisible(true);
            apFenetreAfficheInfo.getChildren().clear();
            if (isbFenetreInfoPersonnalise()) {
                Image imgFenetreInfo = new Image("file:" + getStrFenetreInfoImage());
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setFitWidth(largeurInfo * getFenetreInfoTaille() / 100);
                ivFenetreInfo.setFitHeight(hauteurInfo * getFenetreInfoTaille() / 100);
                ivFenetreInfo.setPreserveRatio(true);
                ivFenetreInfo.setOpacity(getFenetreInfoOpacite());
                Font fonte1 = new Font("Arial", 12);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreInfo.getFitWidth()) / 2 + getFenetreInfoPosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreInfo.getFitHeight()) / 2 + getFenetreInfoPosY() + ivVisualisation.getLayoutY());
                lblFenetreURL.setText(getStrFenetreTexteURL());
                lblFenetreURL.impl_processCSS(true);
                lblFenetreURL.setStyle("-fx-font-size:" + Math.round(getFenetrePoliceTaille() * 10) / 10 + "px;-fx-font-family: \"Arial\";");
                lblFenetreURL.setTextFill(Color.valueOf(getStrFenetreURLCouleur()));
                apFenetreAfficheInfo.getChildren().addAll(ivFenetreInfo);
                double URLPosX = (ivVisualisation.getFitWidth() - lblFenetreURL.prefWidth(-1)) / 2 + getFenetreURLPosX() + ivVisualisation.getLayoutX();
                double URLPosY = (ivVisualisation.getFitHeight() - lblFenetreURL.prefHeight(-1)) / 2 + getFenetreURLPosY() + ivVisualisation.getLayoutY();
                lblFenetreURL.relocate(URLPosX, URLPosY);
            } else {
                Image imgFenetreInfo = new Image("file:" + strRepertAppli + File.separator + "images" + File.separator + "infoDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
            lblFenetreURL.setVisible(false);
        }
    }

    /**
     *
     */
    public void afficheFenetreAide() {
        if (bAfficheFenetreAide) {
            apFenetreAfficheInfo.setVisible(true);
            apFenetreAfficheInfo.getChildren().clear();
            if (isbFenetreAidePersonnalise()) {
                Image imgFenetreAide = new Image("file:" + getStrFenetreAideImage());
                double largeurAide = imgFenetreAide.getWidth();
                double hauteurAide = imgFenetreAide.getHeight();
                ImageView ivFenetreAide = new ImageView(imgFenetreAide);
                ivFenetreAide.setFitWidth(largeurAide * getFenetreAideTaille() / 100);
                ivFenetreAide.setFitHeight(hauteurAide * getFenetreAideTaille() / 100);
                ivFenetreAide.setPreserveRatio(true);
                ivFenetreAide.setOpacity(getFenetreAideOpacite());
                apFenetreAfficheInfo.getChildren().add(ivFenetreAide);
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - ivFenetreAide.getFitWidth()) / 2 + getFenetreAidePosX() + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - ivFenetreAide.getFitHeight()) / 2 + getFenetreAidePosY() + ivVisualisation.getLayoutY());

            } else {
                Image imgFenetreInfo = new Image("file:" + strRepertAppli + File.separator + "images/aideDefaut.jpg");
                ImageView ivFenetreInfo = new ImageView(imgFenetreInfo);
                ivFenetreInfo.setOpacity(0.8);
                apFenetreAfficheInfo.getChildren().add(ivFenetreInfo);
                double largeurInfo = imgFenetreInfo.getWidth();
                double hauteurInfo = imgFenetreInfo.getHeight();
                apFenetreAfficheInfo.setLayoutX((ivVisualisation.getFitWidth() - largeurInfo) / 2 + ivVisualisation.getLayoutX());
                apFenetreAfficheInfo.setLayoutY((ivVisualisation.getFitHeight() - hauteurInfo) / 2 + ivVisualisation.getLayoutY());
            }
        } else {
            apFenetreAfficheInfo.setVisible(false);
        }

    }

    public void affichePlan() {

        apVisuPlan.setVisible(isbAffichePlan());
        if (isbAffichePlan()) {
            double marge = 10.d;
            apVisuPlan.getChildren().clear();
            ImageView ivHSPlanActif = new ImageView(new Image("file:" + strRepertAppli + "/theme/plan/pointActif.png", 12, 12, true, true));
            ImageView ivHSPlan = new ImageView(new Image("file:" + strRepertAppli + "/theme/plan/point.png", 12, 12, true, true));
            Image imgPlan;
            if (iNombrePlans > 0) {
                String strFichier = strRepertTemp + "/images/" + plans[gestionnairePlan.getiPlanActuel()].getStrImagePlan();
                imgPlan = new Image(
                        "file:" + strFichier, getLargeurPlan(), -1, true, true
                );
            } else {
                imgPlan = new Image(
                        "file:" + strRepertAppli + "/theme/plan/planDefaut.jpg", getLargeurPlan(), -1, true, true
                );

            }
            ImageView ivImgPlan = new ImageView(imgPlan);
            apVisuPlan.setPrefSize(imgPlan.getWidth() + marge * 2, imgPlan.getHeight() + marge * 2);
            apVisuPlan.setStyle("-fx-background-color : #" + getStrCouleurFondPlan());
            ivImgPlan.setLayoutX(marge);
            ivImgPlan.setLayoutY(marge);
            apVisuPlan.getChildren().addAll(ivImgPlan, ivHSPlan);

            apVisuPlan.setOpacity(getOpacitePlan());
            double positionX = 0;
            double positionY;
            if (isbAfficheTitre()) {
                positionY = ivVisualisation.getLayoutY() + lblTxtTitre.getHeight();
            } else {
                positionY = ivVisualisation.getLayoutY();
            }
            switch (getStrPositionPlan()) {
                case "left":
                    positionX = ivVisualisation.getLayoutX();
                    break;
                case "right":
                    positionX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuPlan.getPrefWidth();
                    break;
            }
            ivHSPlan.setLayoutX(80);
            ivHSPlan.setLayoutY(40);
            ivHSPlanActif.setLayoutX(30);
            ivHSPlanActif.setLayoutY(90);
            if (isbAfficheRadar()) {
                Arc arcRadar = new Arc(36, 96, getTailleRadar(), getTailleRadar(), -55, 70);
                arcRadar.setType(ArcType.ROUND);
                arcRadar.setFill(getCouleurFondRadar());
                arcRadar.setStroke(getCouleurLigneRadar());
                arcRadar.setOpacity(getOpaciteRadar());
                apVisuPlan.getChildren().addAll(arcRadar, ivHSPlanActif);
            } else {
                apVisuPlan.getChildren().add(ivHSPlanActif);
            }
            apVisuPlan.setLayoutX(positionX);
            apVisuPlan.setLayoutY(positionY);
            if (iNombrePlans > 0) {

                String strRepertImagePlan = strRepertAppli + File.separator + "theme/plan";
                String strImageBoussole1 = "file:" + strRepertImagePlan + "/aiguillePlan.png";
                Image imgBoussole1 = new Image(strImageBoussole1);
                ImageView ivNord = new ImageView(imgBoussole1);

                String strPositX = plans[gestionnairePlan.getiPlanActuel()].getStrPosition().split(":")[1];
                String strPositY = plans[gestionnairePlan.getiPlanActuel()].getStrPosition().split(":")[0];
                positionX = 0;
                positionY = 0;
                switch (strPositX) {
                    case "left":
                        positionX = ivImgPlan.getLayoutX() + plans[gestionnairePlan.getiPlanActuel()].getPositionX();
                        break;
                    case "right":
                        positionX = ivImgPlan.getLayoutX() + imgPlan.getWidth() - imgBoussole1.getWidth() - plans[gestionnairePlan.getiPlanActuel()].getPositionX();
                        break;
                }
                switch (strPositY) {
                    case "top":
                        positionY = ivImgPlan.getLayoutY() + plans[gestionnairePlan.getiPlanActuel()].getPositionY();
                        break;
                    case "bottom":
                        positionY = ivImgPlan.getLayoutY() + imgPlan.getHeight() - imgBoussole1.getHeight() - plans[gestionnairePlan.getiPlanActuel()].getPositionY();
                        break;
                }
                ivNord.setLayoutX(positionX);
                ivNord.setLayoutY(positionY);
                ivNord.setRotate(plans[gestionnairePlan.getiPlanActuel()].getDirectionNord());
                apVisuPlan.getChildren().add(ivNord);
            }
        }
    }

    private void afficheComboMenu() {
        apVisuComboMenu.getChildren().clear();
        if (isbAfficheComboMenu()) {
            apVisuComboMenu.setPrefWidth(302);
            apVisuComboMenu.setPrefHeight(50);
            ImageView ivImageMenu;
            if (isbAfficheComboMenuImages()) {
                ivImageMenu = new ImageView(new Image("file:" + strRepertAppli + "/images/menuAvecImage.jpg"));
            } else {
                ivImageMenu = new ImageView(new Image("file:" + strRepertAppli + "/images/menuSansImage.jpg"));
            }
            apVisuComboMenu.getChildren().add(ivImageMenu);
            double posX = 0, posY = 0;
            switch (getStrPositionXComboMenu()) {
                case "left":
                    posX = ivVisualisation.getLayoutX() + getOffsetXComboMenu();
                    break;
                case "center":
                    posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth()) / 2 + getOffsetXComboMenu();
                    break;
                case "right":
                    posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuComboMenu.getPrefWidth() - getOffsetXComboMenu();
                    break;
            }
            switch (getStrPositionYComboMenu()) {
                case "top":
                    posY = ivVisualisation.getLayoutY() + getOffsetYComboMenu();
                    break;
                case "bottom":
                    posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuComboMenu.getPrefHeight() - getOffsetYComboMenu();
                    break;
            }
            apVisuComboMenu.setLayoutX(posX);
            apVisuComboMenu.setLayoutY(posY);
        }
    }

    /**
     *
     */
    private void afficheVignettes() {
        vbFondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        vbFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - vbFondPrecedent.getPrefWidth()));
        String strPositVert = getStrPositionBarreClassique().split(":")[0];
        String strPositHor = getStrPositionBarreClassique().split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (strPositVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + getOffsetYBarreClassique();
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique();
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - getOffsetYBarreClassique();
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique();
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique();
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth()) / 2 + getOffsetXBarreClassique();
                break;
        }

        String strPositXBoussole = getStrPositionBoussole().split(":")[1];
        String strPositYBoussole = getStrPositionBoussole().split(":")[0];
        double posX = 0;
        double posY = 0;
        switch (strPositXBoussole) {
            case "left":
                posX = ivVisualisation.getLayoutX() + getOffsetXBoussole();
                break;
            case "right":
                posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth();
                break;
        }
        switch (strPositYBoussole) {
            case "bottom":
                posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole();
                break;
            case "top":
                posY = ivVisualisation.getLayoutY() + getOffsetYBoussole();
                break;
        }

        apVisuVignettes.setVisible(isbAfficheVignettes());
        if (isbAfficheVignettes()) {
            ImageView[] ivVignettes = new ImageView[iNombrePanoramiques];
            apVisuVignettes.getChildren().clear();
            apVisuVignettes.setOpacity(getOpaciteVignettes());
            apVisuVignettes.setStyle("-fx-background-color : " + getStrCouleurFondVignettes());
            switch (getStrPositionVignettes()) {
                case "bottom":
                    apVisuVignettes.setPrefHeight(getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes.setPrefWidth(ivVisualisation.getFitWidth());
                    apVisuVignettes.setMinHeight(getTailleImageVignettes() / 2 + 10);
                    apVisuVignettes.setMinWidth(ivVisualisation.getFitWidth());
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apVisuVignettes.getPrefHeight());
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    if (strPositYBoussole.equals("bottom")) {
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - imgBoussole.getFitHeight() - getOffsetYBoussole() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (isbAfficheTitre()) {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes.setMinWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX());
                    if (isbAfficheTitre()) {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight());
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    vbFondPrecedent.setLayoutX(ivVisualisation.getLayoutX() + apVisuVignettes.getPrefWidth());
                    if (strPositHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth();
                    }
                    if (strPositXBoussole.equals("left")) {
                        posX = ivVisualisation.getLayoutX() + getOffsetXBoussole() + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (isbAfficheTitre()) {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight() - lblTxtTitre.getHeight());
                    } else {
                        apVisuVignettes.setPrefHeight(ivVisualisation.getFitHeight());
                        apVisuVignettes.setMinHeight(ivVisualisation.getFitHeight());
                    }
                    apVisuVignettes.setPrefWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes.setMinWidth(getTailleImageVignettes() + 10);
                    apVisuVignettes.setLayoutX(ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apVisuVignettes.getPrefWidth());
                    if (isbAfficheTitre()) {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY() + lblTxtTitre.getHeight());
                    } else {
                        apVisuVignettes.setLayoutY(ivVisualisation.getLayoutY());
                    }
                    vbFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - vbFondPrecedent.getPrefWidth()) - apVisuVignettes.getPrefWidth());
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    if (strPositXBoussole.equals("right")) {
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getOffsetXBoussole() - imgBoussole.getFitWidth() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
            int iMaxVignettes = 5;
            int iNombre = (iNombrePanoramiques > iMaxVignettes) ? iMaxVignettes : iNombrePanoramiques;
            for (int i = 0; i < iNombre; i++) {
                ivVignettes[i] = new ImageView(panoramiquesProjet[i].getImgVignettePanoramique());
                ivVignettes[i].setFitWidth(getTailleImageVignettes());
                ivVignettes[i].setFitHeight(getTailleImageVignettes() / 2);
                switch (getStrPositionVignettes()) {
                    case "bottom":
                        ivVignettes[i].setLayoutX((getTailleImageVignettes() + 10) * i + 5);
                        ivVignettes[i].setLayoutY(5);
                        break;
                    case "left":
                        ivVignettes[i].setLayoutY((getTailleImageVignettes() / 2 + 10) * i + 5);
                        ivVignettes[i].setLayoutX(5);
                        break;
                    case "right":
                        ivVignettes[i].setLayoutY((getTailleImageVignettes() / 2 + 10) * i + 5);
                        ivVignettes[i].setLayoutX(5);
                        break;
                }
                apVisuVignettes.getChildren().add(ivVignettes[i]);
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
        imgBoussole.setLayoutX(posX);
        imgBoussole.setLayoutY(posY);
        imgAiguille.setLayoutX(posX + (imgBoussole.getFitWidth() - imgAiguille.getFitWidth()) / 2);
        imgAiguille.setLayoutY(posY);

    }

    private int chercheZone(int nb) {
        int iNumero = -1;
        String strZone = "area-" + nb;
        for (int i = 0; i < getiNombreZonesBarrePersonnalisee(); i++) {
            //System.out.println(i + "==>" + getiNombreZonesBarrePersonnalisee());
            if (getZonesBarrePersonnalisee()[i].getStrIdZone().equals(strZone)) {
                iNumero = i;
            }
        }
        return iNumero;
    }

    public void afficheBarrePersonnalisee() {
        apAfficheBarrePersonnalisee.getChildren().clear();
        if (!tfLienImageBarrePersonnalisee.getText().equals("")) {
            if (getStrVisibiliteBarrePersonnalisee().equals("oui")) {
                changeCouleurBarrePersonnalisee(couleurBarrePersonnalisee.getHue(), couleurBarrePersonnalisee.getSaturation(), couleurBarrePersonnalisee.getBrightness());
                ivBarrePersonnalisee.setImage(getWiBarrePersonnaliseeCouleur());
                ivBarrePersonnalisee.setPreserveRatio(true);
                ivBarrePersonnalisee.setSmooth(true);
                double hauteur = getWiBarrePersonnaliseeCouleur().getHeight();
                double largeur = getWiBarrePersonnaliseeCouleur().getWidth();
                if (largeur > hauteur) {
                    ivBarrePersonnalisee.setFitWidth(150);
                } else {
                    ivBarrePersonnalisee.setFitHeight(150);
                }
                ImageView ivAfficheBarrePersonnalisee = new ImageView(getWiBarrePersonnaliseeCouleur());
                ivAfficheBarrePersonnalisee.setSmooth(true);

                apAfficheBarrePersonnalisee.getChildren().add(ivAfficheBarrePersonnalisee);
                apAfficheBarrePersonnalisee.setPrefHeight(hauteur);
                apAfficheBarrePersonnalisee.setPrefWidth(largeur);
                apAfficheBarrePersonnalisee.setScaleX(getTailleBarrePersonnalisee() / 100.d);
                apAfficheBarrePersonnalisee.setScaleY(getTailleBarrePersonnalisee() / 100.d);
                String strPositVert = getStrPositionBarrePersonnalisee().split(":")[0];
                String strPositHor = getStrPositionBarrePersonnalisee().split(":")[1];
                double ajoutX = (largeur / 2.d) * getTailleBarrePersonnalisee() / 100.d;
                double ajoutY = (hauteur / 2.d) * getTailleBarrePersonnalisee() / 100.d;
                double LX = 0;
                double LY = 0;
                switch (strPositVert) {
                    case "top":
                        LY = ivVisualisation.getLayoutY() + getOffsetYBarrePersonnalisee() + ajoutY - hauteur / 2.d;
                        break;
                    case "bottom":
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - getOffsetYBarrePersonnalisee() - ajoutY + hauteur / 2.d;
                        break;
                    case "middle":
                        LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight()) / 2.d - getOffsetYBarrePersonnalisee();
                        break;
                }

                switch (strPositHor) {
                    case "right":
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - getOffsetXBarrePersonnalisee() - ajoutX + largeur / 2.d;
                        break;
                    case "left":
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarrePersonnalisee() + ajoutX - largeur / 2.d;
                        break;
                    case "center":
                        LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth()) / 2 + getOffsetXBarrePersonnalisee();
                        break;
                }
                if (isbAfficheVignettes()) {
                    switch (getStrPositionVignettes()) {
                        case "bottom":
                            if (strPositVert.equals("bottom")) {
                                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - apAfficheBarrePersonnalisee.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight() + ajoutY - hauteur / 2.d;
                            }
                            break;
                        case "left":
                            if (strPositHor.equals("left")) {
                                LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth() + ajoutX - largeur / 2.d;
                            }
                            break;
                        case "right":
                            if (strPositHor.equals("right")) {
                                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - apAfficheBarrePersonnalisee.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth() - ajoutX + largeur / 2.d;
                            }
                            break;
                    }
                }
                apAfficheBarrePersonnalisee.setLayoutX(LX);
                apAfficheBarrePersonnalisee.setLayoutY(LY);
                Circle[] cercles = new Circle[50];
                int zone = 1;
                if (getStrInfoBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivInfoBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/info.png"));
                        ivInfoBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivInfoBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivInfoBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivInfoBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivInfoBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrAideBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivAideBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/aide.png"));
                        ivAideBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivAideBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivAideBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivAideBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivAideBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrRotationBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivRotationBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/rotation.png"));
                        ivRotationBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivRotationBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivRotationBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivRotationBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivRotationBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrSourisBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivSourisBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/souris.png"));
                        ivSourisBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivSourisBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivSourisBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivSourisBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivSourisBarrePersonnalisee);
                    }
                    zone++;
                }
                if (getStrPleinEcranBarrePersonnalisee().equals("oui")) {
                    int iNum = chercheZone(zone);
                    if (iNum != -1) {
                        ImageView ivFSBarrePersonnalisee = new ImageView(new Image("file:" + strRepertAppli + "/theme/telecommandes/fs.png"));
                        ivFSBarrePersonnalisee.setFitWidth(getTailleIconesBarrePersonnalisee());
                        ivFSBarrePersonnalisee.setFitHeight(getTailleIconesBarrePersonnalisee());
                        ivFSBarrePersonnalisee.setLayoutX(getZonesBarrePersonnalisee()[iNum].getCentre().getX() - getTailleIconesBarrePersonnalisee() / 2);
                        ivFSBarrePersonnalisee.setLayoutY(getZonesBarrePersonnalisee()[iNum].getCentre().getY() - getTailleIconesBarrePersonnalisee() / 2);
                        apAfficheBarrePersonnalisee.getChildren().add(ivFSBarrePersonnalisee);
                    }
                    zone++;
                }
            }
        }
    }

    public int lisFichierShp(String strNomFichier, ZoneTelecommande[] zonesBarre) throws UnsupportedEncodingException, IOException {
        try {
            File fileFichier = new File(strNomFichier);
            if (fileFichier.exists()) {
                int iNombreZonesBarre;
                String strTexte;
                try (BufferedReader brLisFichier = new BufferedReader(new InputStreamReader(
                        new FileInputStream(strNomFichier), "UTF-8"))) {
                    strTexte = "";
                    String strLigneTexte;
                    iNombrePanoramiquesFichier = 0;
                    while ((strLigneTexte = brLisFichier.readLine()) != null) {
                        if (strLigneTexte.contains("Panoramique=>")) {
                            iNombrePanoramiquesFichier++;
                            //System.out.println("nombre pano fichier : " + nombrePanoramiquesFichier);
                        }
                        strTexte += strLigneTexte + "\n";
                    }
                    brLisFichier.close();
                    cbDeplacementsBarrePersonnalisee.setSelected(true);
                    cbDeplacementsBarrePersonnalisee.setDisable(false);
                    setStrDeplacementsBarrePersonnalisee("oui");
                    cbZoomBarrePersonnalisee.setSelected(true);
                    cbZoomBarrePersonnalisee.setDisable(false);
                    setStrZoomBarrePersonnalisee("oui");
                    cbRotationBarrePersonnalisee.setSelected(true);
                    cbRotationBarrePersonnalisee.setDisable(false);
                    setStrRotationBarrePersonnalisee("oui");
                    cbSourisBarrePersonnalisee.setSelected(true);
                    cbSourisBarrePersonnalisee.setDisable(false);
                    setStrSourisBarrePersonnalisee("oui");
                    cbFSBarrePersonnalisee.setSelected(true);
                    cbFSBarrePersonnalisee.setDisable(false);
                    setStrPleinEcranBarrePersonnalisee("oui");
                    setStrInfoBarrePersonnalisee("oui");
                    setStrAideBarrePersonnalisee("oui");
                    tfLien1BarrePersonnalisee.setDisable(true);
                    tfLien2BarrePersonnalisee.setDisable(true);
                    String strLigneComplete = strTexte.replace("[", "|");
                    String strLignes[] = strLigneComplete.split("\\|", 500);
                    String strLigne;
                    String[] strElementsLigne;
                    String[] strTypeElement;
                    iNombreZonesBarre = 0;
                    for (int ikk = 1; ikk < strLignes.length; ikk++) {
                        strLigne = strLignes[ikk];
                        strElementsLigne = strLigne.split(";", 25);
                        strTypeElement = strElementsLigne[0].split(">", 2);
                        strTypeElement[0] = strTypeElement[0].replace(" ", "").replace("=", "").replace("[", "");
                        strElementsLigne[0] = strTypeElement[1];
                        if ("area".equals(strTypeElement[0])) {
                            zonesBarre[iNombreZonesBarre] = new ZoneTelecommande();
                            for (int i = 0; i < strElementsLigne.length; i++) {
                                strElementsLigne[i] = strElementsLigne[i].replace("]", "").replace("\n", "");
                                String[] strValeur = strElementsLigne[i].split(":", 2);
                                //System.out.println(valeur[0] + "=" + valeur[1]);

                                switch (strValeur[0]) {
                                    case "id":
                                        zonesBarre[iNombreZonesBarre].setStrIdZone(strValeur[1]);
                                        break;
                                    case "shape":
                                        zonesBarre[iNombreZonesBarre].setStrTypeZone(strValeur[1]);
                                        break;
                                    case "coords":
                                        zonesBarre[iNombreZonesBarre].setStrCoordonneesZone(strValeur[1]);
                                        break;
                                }
                            }
                            zonesBarre[iNombreZonesBarre].calculeCentre();
                            //System.out.println("id : " + zonesBarre[nombreZonesBarre].getStrIdZone()
                            // + ", shape : " + zonesBarre[nombreZonesBarre].getStrTypeZone()
                            // + ", coord : " + zonesBarre[nombreZonesBarre].getStrCoordonneesZone()
                            // + ", centre : " + zonesBarre[nombreZonesBarre].getCentre().getX()
                            // + ";" + zonesBarre[nombreZonesBarre].getCentre().getY()
                            //);
                            switch (zonesBarre[iNombreZonesBarre].getStrIdZone()) {
                                case "telUp":
                                case "telDown":
                                case "telRight":
                                case "telLeft":
                                    cbDeplacementsBarrePersonnalisee.setSelected(false);
                                    cbDeplacementsBarrePersonnalisee.setDisable(true);
                                    setStrDeplacementsBarrePersonnalisee("non");
                                    break;
                                case "telZoomMoins":
                                case "telZoomPlus":
                                    cbZoomBarrePersonnalisee.setSelected(false);
                                    cbZoomBarrePersonnalisee.setDisable(true);
                                    setStrZoomBarrePersonnalisee("non");
                                    break;
                                case "telInfo":
                                    setStrInfoBarrePersonnalisee("non");
                                    break;
                                case "telAide":
                                    setStrAideBarrePersonnalisee("non");
                                    break;
                                case "telFS":
                                    cbFSBarrePersonnalisee.setSelected(false);
                                    cbFSBarrePersonnalisee.setDisable(true);
                                    setStrPleinEcranBarrePersonnalisee("non");
                                    break;
                                case "telSouris":
                                    cbSourisBarrePersonnalisee.setSelected(false);
                                    cbSourisBarrePersonnalisee.setDisable(true);
                                    setStrSourisBarrePersonnalisee("non");
                                    break;
                                case "telRotation":
                                    cbRotationBarrePersonnalisee.setSelected(false);
                                    cbRotationBarrePersonnalisee.setDisable(true);
                                    setStrRotationBarrePersonnalisee("non");
                                    break;
                                case "telLien-1":
                                    tfLien1BarrePersonnalisee.setDisable(false);
                                    break;
                                case "telLien-2":
                                    tfLien2BarrePersonnalisee.setDisable(false);
                                    break;
                            }
                            iNombreZonesBarre++;
                        }

                    }
                }
                return iNombreZonesBarre;
            } else {
                return -1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditeurPanovisu.class
                    .getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public void choixBarrePersonnalisee() throws IOException {
        strRepertBarrePersonnalisee = strRepertAppli + "/theme/telecommandes";
        File fileRepert;
        fileRepert = new File(strRepertBarrePersonnalisee);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter efShpFilter = new FileChooser.ExtensionFilter("Fichiers barre personnalisée (SHP)", "*.shp", "*.png");

        fileChooser.setInitialDirectory(fileRepert);
        fileChooser.getExtensionFilters().addAll(efShpFilter);

        File fileFichierImage = fileChooser.showOpenDialog(null);
        if (fileFichierImage != null) {
            String strNomFichier = fileFichierImage.getAbsolutePath();
            strNomFichier = strNomFichier.substring(0, strNomFichier.length() - 4);
            String strNomFichierShp = strNomFichier + ".shp";
            String strNomFichierPng = strNomFichier + ".png";
            File fileFichShp = new File(strNomFichierShp);
            File fileFichPng = new File(strNomFichierPng);
            if (fileFichShp.exists() && fileFichPng.exists()) {
                setiNombreZonesBarrePersonnalisee(lisFichierShp(strNomFichierShp, getZonesBarrePersonnalisee()));
                if (getiNombreZonesBarrePersonnalisee() != -1) {
                    btnEditerBarre.setDisable(false);
                    setStrLienImageBarrePersonnalisee(strNomFichierPng);
                    tfLienImageBarrePersonnalisee.setText(strNomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + strNomFichierPng);
                    afficheBarrePersonnalisee();
                }
            }
        }
    }

    public void chargeBarrePersonnalisee(String strNomFichier) throws IOException {
        if (strNomFichier.length() > 4) {
            strNomFichier = strNomFichier.substring(0, strNomFichier.length() - 4);
            String strNomFichierShp = strNomFichier + ".shp";
            String strNomFichierPng = strNomFichier + ".png";
            //System.out.println("Image : " + nomFichierPng);
            File fileFichShp = new File(strNomFichierShp);
            File fileFichPng = new File(strNomFichierPng);
            if (fileFichShp.exists() && fileFichPng.exists()) {
                setiNombreZonesBarrePersonnalisee(lisFichierShp(strNomFichierShp, getZonesBarrePersonnalisee()));
                if (getiNombreZonesBarrePersonnalisee() != -1) {
                    btnEditerBarre.setDisable(false);
                    tfLienImageBarrePersonnalisee.setText(strNomFichierPng);
                    imgPngBarrePersonnalisee = new Image("file:" + strNomFichierPng);
                    afficheBarrePersonnalisee();
                }
            }
        }
    }

    /**
     *
     * @param strPosition
     * @param dX
     * @param dY
     * @param taille
     * @param strStyleBoutons
     * @param strStyleHS
     * @param espacement
     */
    public void afficheBarreClassique(String strPosition, double dX, double dY, double taille, String strStyleBoutons, String strStyleHS, double espacement) {
        String strRepertBoutons = "file:" + strRepertBoutonsPrincipal + File.separator + strStyleBoutons;
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation);
        if (getiNombreImagesFond() > 0) {
            for (int i = 0; i < getiNombreImagesFond(); i++) {
                ImageView ivImageFond = new ImageView(getImagesFond()[i].getImgFond());
                ivImageFond.setFitWidth(getImagesFond()[i].getTailleX());
                ivImageFond.setFitHeight(getImagesFond()[i].getTailleY());
                double posX = 0, posY = 0;
                switch (getImagesFond()[i].getStrPosX()) {
                    case "left":
                        posX = getImagesFond()[i].getOffsetX() + ivVisualisation.getLayoutX();
                        break;
                    case "center":
                        posX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - getImagesFond()[i].getTailleX()) / 2 + getImagesFond()[i].getOffsetX();
                        break;
                    case "right":
                        posX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - getImagesFond()[i].getOffsetX() - getImagesFond()[i].getTailleX();
                        break;
                }
                switch (getImagesFond()[i].getStrPosY()) {
                    case "top":
                        posY = getImagesFond()[i].getOffsetY() + ivVisualisation.getLayoutY();
                        break;
                    case "middle":
                        posY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - getImagesFond()[i].getTailleY()) / 2 + getImagesFond()[i].getOffsetY();
                        break;
                    case "bottom":
                        posY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - getImagesFond()[i].getOffsetY() - getImagesFond()[i].getTailleY();
                        break;
                }
                if (isbAfficheVignettes()) {
                    switch (getStrPositionVignettes()) {
                        case "bottom":
                            if (getImagesFond()[i].getStrPosY().equals("bottom")) {
                                posY -= (getTailleImageVignettes() / 2 + 6);
                            }
                            break;
                        case "left":
                            if (getImagesFond()[i].getStrPosX().equals("left")) {
                                posX += getTailleImageVignettes() + 10;
                            }
                            break;
                        case "right":
                            if (getImagesFond()[i].getStrPosX().equals("right")) {
                                posX -= (getTailleImageVignettes() + 10);
                            }
                            break;
                    }
                }
                ivImageFond.setLayoutX(posX);
                ivImageFond.setLayoutY(posY);
                ivImageFond.setOpacity(getImagesFond()[i].getOpacite());
                apVisualisation.getChildren().add(ivImageFond);
            }
        }
        apVisualisation.getChildren().addAll(lblTxtTitre, imgBoussole, imgAiguille, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, apVisuPlan, vbFondSuivant, vbFondPrecedent);
        lblTxtTitre.setVisible(isbAfficheTitre());
        chargeBarre(strStyleBoutons, strStyleHS, getStrImageMasque());
        afficheMasque();
        hbbarreBoutons = new HBox(espacement);
        hbbarreBoutons.setId("barreBoutons");
        hbbarreBoutons.setVisible(getStrVisibiliteBarreClassique().equals("oui"));
        hbbarreBoutons.setTranslateZ(1);
        ivHotSpot.setFitWidth(30);
        ivHotSpot.setPreserveRatio(true);
        ivHotSpot.setLayoutX(510);
        ivHotSpot.setLayoutY(310);
        ivHotSpot.setTranslateZ(1);
        Tooltip tltpHSPano = new Tooltip("Hotspot panoramique");
        tltpHSPano.setStyle(strTooltipStyle);
        Tooltip.install(ivHotSpot, tltpHSPano);
        ivHotSpotImage.setFitWidth(30);
        ivHotSpotImage.setPreserveRatio(true);
        ivHotSpotImage.setLayoutX(560);
        ivHotSpotImage.setLayoutY(310);
        ivHotSpotImage.setTranslateZ(1);
        Tooltip tltpHSImage = new Tooltip("Hotspot Photo");
        tltpHSImage.setStyle(strTooltipStyle);
        Tooltip.install(ivHotSpotImage, tltpHSImage);
        int iNombreBoutons = 11;
        if (getStrDeplacementsBarreClassique().equals("non")) {
            iNombreBoutons -= 4;
        }
        if (getStrZoomBarreClassique().equals("non")) {
            iNombreBoutons -= 2;
        }
        if (getStrOutilsBarreClassique().equals("non")) {
            iNombreBoutons -= 5;
        } else {
            if (getStrPleinEcranBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
            if (getStrRotationBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
            if (getStrSourisBarreClassique().equals("non")) {
                iNombreBoutons -= 1;
            }
        }
        double tailleBarre1 = (double) iNombreBoutons * ((double) taille + espacement);
        hbbarreBoutons.setPrefWidth(tailleBarre1);
        hbbarreBoutons.setPrefHeight((double) taille);
        hbbarreBoutons.setMinWidth(tailleBarre1);
        hbbarreBoutons.setMinHeight((double) taille);
        hbbarreBoutons.setMaxWidth(tailleBarre1);
        hbbarreBoutons.setMaxHeight((double) taille);
        hbDeplacements = new HBox(espacement);
        hbZoom = new HBox(espacement);
        hbOutils = new HBox(espacement);
        hbbarreBoutons.getChildren().addAll(hbDeplacements, hbZoom, hbOutils);
        if (getStrDeplacementsBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbDeplacements);
        }
        if (getStrZoomBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbZoom);
        }
        if (getStrOutilsBarreClassique().equals("non")) {
            hbbarreBoutons.getChildren().remove(hbOutils);
        }
        apVisualisation.getChildren().addAll(hbbarreBoutons, ivHotSpot, ivHotSpotImage, apAfficheDiapo, apFenetreAfficheInfo, lblFenetreURL, ivDiapo, apAfficheBarrePersonnalisee);
        ivHaut.setFitWidth(taille);
        ivHaut.setFitHeight(taille);
        ivBas.setFitWidth(taille);
        ivBas.setFitHeight(taille);
        ivGauche.setFitWidth(taille);
        ivGauche.setFitHeight(taille);
        ivDroite.setFitWidth(taille);
        ivDroite.setFitHeight(taille);
        ivZoomPlus.setFitWidth(taille);
        ivZoomPlus.setFitHeight(taille);
        ivZoomMoins.setFitWidth(taille);
        ivZoomMoins.setFitHeight(taille);
        ivInfo.setFitWidth(taille);
        ivInfo.setFitHeight(taille);
        ivPleinEcran.setFitWidth(taille);
        ivPleinEcran.setFitHeight(taille);
        ivModeSouris.setFitWidth(taille);
        ivModeSouris.setFitHeight(taille);
        ivAide.setFitWidth(taille);
        ivAide.setFitHeight(taille);
        ivAutoRotation.setFitWidth(taille);
        ivAutoRotation.setFitHeight(taille);
        hbDeplacements.getChildren().addAll(ivGauche, ivHaut, ivBas, ivDroite);
        hbZoom.getChildren().addAll(ivZoomPlus, ivZoomMoins);
        hbOutils.getChildren().addAll(ivPleinEcran, ivModeSouris, ivAutoRotation, ivInfo, ivAide);
        if (getStrPleinEcranBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivPleinEcran);
        }
        if (getStrSourisBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivModeSouris);
        }
        if (getStrRotationBarreClassique().equals("non")) {
            hbOutils.getChildren().remove(ivAutoRotation);
        }
        String strPositVert = strPosition.split(":")[0];
        String strPositHor = strPosition.split(":")[1];
        double LX = 0;
        double LY = 0;
        switch (strPositVert) {
            case "top":
                LY = ivVisualisation.getLayoutY() + dY;
                break;
            case "bottom":
                LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - dY;
                break;
            case "middle":
                LY = ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight()) / 2.d - dY;
                break;
        }

        switch (strPositHor) {
            case "right":
                LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - dX;
                break;
            case "left":
                LX = ivVisualisation.getLayoutX() + dX;
                break;
            case "center":
                LX = ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth()) / 2 + dX;
                break;
        }
        if (isbAfficheVignettes()) {
            switch (getStrPositionVignettes()) {
                case "bottom":
                    if (strPositVert.equals("bottom")) {
                        LY = ivVisualisation.getLayoutY() + ivVisualisation.getFitHeight() - hbbarreBoutons.getPrefHeight() - getOffsetYBarreClassique() - apVisuVignettes.getPrefHeight();
                    }
                    break;
                case "left":
                    if (strPositHor.equals("left")) {
                        LX = ivVisualisation.getLayoutX() + getOffsetXBarreClassique() + apVisuVignettes.getPrefWidth();
                    }
                    break;
                case "right":
                    if (strPositHor.equals("right")) {
                        LX = ivVisualisation.getLayoutX() + ivVisualisation.getFitWidth() - hbbarreBoutons.getPrefWidth() - getOffsetXBarreClassique() - apVisuVignettes.getPrefWidth();
                    }
                    break;
            }
        }
        hbbarreBoutons.setLayoutX(LX);
        hbbarreBoutons.setLayoutY(LY);
    }

    /**
     *
     * @param repertoire
     * @return
     */
    private ArrayList<String> strListerStyle(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles();
        for (File fileRepert : fileRepertoires) {
            if (fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                if (!strNomRepert.equals("hotspots")) {
                    strListe.add(strNomRepert);
                }
            }
        }
        return strListe;
    }

    /**
     *
     * @param strRepertoire
     * @return
     */
    private ArrayList<String> strListerHotSpots(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(IMAGE_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                strListe.add(strNomRepert);
            }
        }
        return strListe;
    }

    private ArrayList<String> strListerBoussoles(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(PNG_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                if (strNomRepert.contains("rose")) {
                    strListe.add(strNomRepert);
                }
            }
        }
        return strListe;
    }

    private ArrayList<String> strListerMasques(String strRepertoire) {
        ArrayList<String> strListe = new ArrayList<>();
        File[] fileRepertoires = new File(strRepertoire).listFiles(PNG_FILTER);
        for (File fileRepert : fileRepertoires) {
            if (!fileRepert.isDirectory()) {
                String strNomRepert = fileRepert.getName();
                strListe.add(strNomRepert);
            }
        }
        return strListe;
    }

    /**
     *
     * @return
     */
    public String strGetTemplate() {
        String strContenuFichier = "";
        strContenuFichier
                += "styleBarre=" + getStyleBarreClassique() + "\n"
                + "couleurTheme=" + couleurTheme.toString().substring(2, 8) + "\n"
                + "couleurBoutons=" + couleurBarreClassique.toString().substring(2, 8) + "\n"
                + "couleurHotspots=" + couleurHotspots.toString().substring(2, 8) + "\n"
                + "couleurHotspotsPhoto=" + couleurHotspotsPhoto.toString().substring(2, 8) + "\n"
                + "couleurMasque=" + couleurMasque.toString().substring(2, 8) + "\n"
                + "styleHotspots=" + getStrStyleHotSpots() + "\n"
                + "styleHotspotImages=" + getStrStyleHotSpotImages() + "\n"
                + "position=" + getStrPositionBarreClassique() + "\n"
                + "dX=" + Math.round(getOffsetXBarreClassique()) + "\n"
                + "dY=" + Math.round(getOffsetYBarreClassique()) + "\n"
                + "visible=" + getStrVisibiliteBarreClassique() + "\n"
                + "suivantPrecedent=" + isbSuivantPrecedent() + "\n"
                + "deplacement=" + getStrDeplacementsBarreClassique() + "\n"
                + "zoom=" + getStrZoomBarreClassique() + "\n"
                + "outils=" + getStrOutilsBarreClassique() + "\n"
                + "rotation=" + getStrRotationBarreClassique() + "\n"
                + "FS=" + getStrPleinEcranBarreClassique() + "\n"
                + "souris=" + getStrSourisBarreClassique() + "\n"
                + "espacementBoutons=" + getEspacementBarreClassique() + "\n"
                + "bCouleurOrigineBarrePersonnalisee=" + isbCouleurOrigineBarrePersonnalisee() + "\n"
                + "nombreZonesBarrePersonnalisee=" + getiNombreZonesBarrePersonnalisee() + "\n"
                + "offsetXBarrePersonnalisee=" + getOffsetXBarrePersonnalisee() + "\n"
                + "offsetYBarrePersonnalisee=" + getOffsetYBarrePersonnalisee() + "\n"
                + "tailleBarrePersonnalisee=" + getTailleBarrePersonnalisee() + "\n"
                + "tailleIconesBarrePersonnalisee=" + getTailleIconesBarrePersonnalisee() + "\n"
                + "strPositionBarrePersonnalisee=" + getStrPositionBarrePersonnalisee() + "\n"
                + "strDeplacementsBarrePersonnalisee=" + getStrDeplacementsBarrePersonnalisee() + "\n"
                + "strZoomBarrePersonnalisee=" + getStrZoomBarrePersonnalisee() + "\n"
                + "strInfoBarrePersonnalisee=" + getStrInfoBarrePersonnalisee() + "\n"
                + "strAideBarrePersonnalisee=" + getStrAideBarrePersonnalisee() + "\n"
                + "strRotationBarrePersonnalisee=" + getStrRotationBarrePersonnalisee() + "\n"
                + "strPleinEcranBarrePersonnalisee=" + getStrPleinEcranBarrePersonnalisee() + "\n"
                + "strSourisBarrePersonnalisee=" + getStrSourisBarrePersonnalisee() + "\n"
                + "strVisibiliteBarrePersonnalisee=" + getStrVisibiliteBarrePersonnalisee() + "\n"
                + "strLienImageBarrePersonnalisee=" + getStrLienImageBarrePersonnalisee() + "\n"
                + "strLien1BarrePersonnalisee=" + getStrLien1BarrePersonnalisee() + "\n"
                + "strLien2BarrePersonnalisee=" + getStrLien1BarrePersonnalisee() + "\n"
                + "couleurBarrePersonnalisee=" + couleurBarrePersonnalisee + "\n"
                + "afficheTitre=" + isbAfficheTitre() + "\n"
                + "titrePolice=" + getStrTitrePoliceNom() + "\n"
                + "titrePoliceTaille=" + getStrTitrePoliceTaille() + "\n"
                + "titreOpacite=" + Math.round(getTitreOpacite() * 100.d) / 100.d + "\n"
                + "titreTaille=" + getTitreTaille() + "\n"
                + "titreCouleur=" + getStrCouleurTitre() + "\n"
                + "titreFondCouleur=" + getStrCouleurFondTitre() + "\n"
                + "bFenetreInfoPersonnalise=" + isbFenetreInfoPersonnalise() + "\n"
                + "bFenetreAidePersonnalise=" + isbFenetreAidePersonnalise() + "\n"
                + "strFenetreInfoImage =" + getStrFenetreInfoImage() + "\n"
                + "strFenetreAideImage=" + getStrFenetreAideImage() + "\n"
                + "fenetreInfoTaille=" + getFenetreInfoTaille() + "\n"
                + "fenetreAideTaille=" + getFenetreAideTaille() + "\n"
                + "fenetreInfoPosX=" + getFenetreInfoPosX() + "\n"
                + "fenetreInfoPosY=" + getFenetreInfoPosY() + "\n"
                + "fenetreAidePosX=" + getFenetreAidePosX() + "\n"
                + "fenetreAidePosY=" + getFenetreAidePosY() + "\n"
                + "fenetreInfoOpacite=" + getFenetreInfoOpacite() + "\n"
                + "fenetreAideOpacite=" + getFenetreAideOpacite() + "\n"
                + "strFenetreURL=" + getStrFenetreURL() + "\n"
                + "strFenetreTexteURL=" + getStrFenetreTexteURL() + "\n"
                + "strFenetreURLInfobulle=" + getStrFenetreURLInfobulle() + "\n"
                + "strFenetreURLCouleur=" + getStrFenetreURLCouleur() + "\n"
                + "strFenetrePolice=" + getStrFenetrePolice() + "\n"
                + "fenetrePoliceTaille=" + getFenetrePoliceTaille() + "\n"
                + "fenetreURLPosX=" + getFenetreURLPosX() + "\n"
                + "fenetreURLPosY=" + getFenetreURLPosY() + "\n"
                + "strFenetreCouleurFond=" + getStrFenetreCouleurFond() + "\n"
                + "fenetreOpaciteFond=" + getFenetreOpaciteFond() + "\n"
                + "diaporamaOpacite=" + Math.round(getDiaporamaOpacite() * 100.d) / 100.d + "\n"
                + "diaporamaCouleur=" + getStrCouleurDiaporama() + "\n"
                + "afficheBoussole=" + isbAfficheBoussole() + "\n"
                + "imageBoussole=" + getStrImageBoussole() + "\n"
                + "tailleBoussole=" + Math.round(getTailleBoussole() * 10.d) / 10.d + "\n"
                + "positionBoussole=" + getStrPositionBoussole() + "\n"
                + "dXBoussole=" + Math.round(getOffsetXBoussole()) + "\n"
                + "dYBoussole=" + Math.round(getOffsetYBoussole()) + "\n"
                + "opaciteBoussole=" + Math.round(getOpaciteBoussole() * 100.d) / 100.d + "\n"
                + "aiguilleMobile=" + isbAiguilleMobileBoussole() + "\n"
                + "afficheMasque=" + isbAfficheMasque() + "\n"
                + "imageMasque=" + getStrImageMasque() + "\n"
                + "tailleMasque=" + Math.round(getTailleMasque() * 10.d) / 10.d + "\n"
                + "positionMasque=" + getStrPositionMasque() + "\n"
                + "dXMasque=" + Math.round(getdXMasque()) + "\n"
                + "dYMasque=" + Math.round(getdYMasque()) + "\n"
                + "opaciteMasque=" + Math.round(getOpaciteMasque() * 100.d) / 100.d + "\n"
                + "masqueNavigation=" + isbMasqueNavigation() + "\n"
                + "masqueBoussole=" + isbMasqueBoussole() + "\n"
                + "masqueTitre=" + isbMasqueTitre() + "\n"
                + "masquePlan=" + isbMasquePlan() + "\n"
                + "masqueReseaux=" + isbMasqueReseaux() + "\n"
                + "masqueVignettes=" + isbMasqueVignettes() + "\n"
                + "masqueCombo=" + isbMasqueCombo() + "\n"
                + "masqueSuivPrec=" + isbMasqueSuivPrec() + "\n"
                + "masqueHotspots=" + isbMasqueHotspots() + "\n"
                + "afficheReseauxSociaux=" + isbAfficheReseauxSociaux() + "\n"
                + "tailleReseauxSociaux=" + Math.round(getTailleReseauxSociaux() * 10.d) / 10.d + "\n"
                + "positionReseauxSociaux=" + getStrPositionReseauxSociaux() + "\n"
                + "dXReseauxSociaux=" + Math.round(getdXReseauxSociaux()) + "\n"
                + "dYReseauxSociaux=" + Math.round(getdYReseauxSociaux()) + "\n"
                + "opaciteReseauxSociaux=" + Math.round(getOpaciteReseauxSociaux() * 100.d) / 100.d + "\n"
                + "masqueTwitter=" + isbReseauxSociauxTwitter() + "\n"
                + "masqueGoogle=" + isbReseauxSociauxGoogle() + "\n"
                + "masqueFacebook=" + isbReseauxSociauxFacebook() + "\n"
                + "masqueEmail=" + isbReseauxSociauxEmail() + "\n"
                + "afficheVignettes=" + isbAfficheVignettes() + "\n"
                + "positionVignettes=" + getStrPositionVignettes() + "\n"
                + "opaciteVignettes=" + Math.round(getOpaciteVignettes() * 100.d) / 100.d + "\n"
                + "tailleImageVignettes=" + Math.round(getTailleImageVignettes()) + "\n"
                + "couleurFondVignettes=" + getStrCouleurFondVignettes() + "\n"
                + "couleurTexteVignettes=" + getStrCouleurTexteVignettes() + "\n"
                + "bAfficheComboMenu=" + isbAfficheComboMenu() + "\n"
                + "bAfficheComboMenuImages=" + isbAfficheComboMenuImages() + "\n"
                + "positionXComboMenu=" + getStrPositionXComboMenu() + "\n"
                + "positionYComboMenu=" + getStrPositionYComboMenu() + "\n"
                + "offsetXComboMenu=" + getOffsetXComboMenu() + "\n"
                + "offsetYComboMenu=" + getOffsetYComboMenu() + "\n"
                + "affichePlan=" + isbAffichePlan() + "\n"
                + "positionPlan=" + getStrPositionPlan() + "\n"
                + "opacitePlan=" + Math.round(getOpacitePlan() * 100.d) / 100.d + "\n"
                + "largeurPlan=" + Math.round(getLargeurPlan()) + "\n"
                + "couleurFondPlan=" + getStrCouleurFondPlan() + "\n"
                + "couleurTextePlan=" + getStrCouleurTextePlan() + "\n"
                + "afficheRadar=" + isbAfficheRadar() + "\n"
                + "opaciteRadar=" + Math.round(getOpaciteRadar() * 100.d) / 100.d + "\n"
                + "tailleRadar=" + Math.round(getTailleRadar()) + "\n"
                + "couleurFondRadar=" + getStrCouleurFondRadar() + "\n"
                + "couleurLigneRadar=" + getStrCouleurLigneRadar() + "\n"
                + "afficheMenuContextuel=" + isbAfficheMenuContextuel() + "\n"
                + "affichePrecSuivMC=" + isbAffichePrecSuivMC() + "\n"
                + "affichePlaneteNormalMC=" + isbAffichePlanetNormalMC() + "\n"
                + "affichePersMC1=" + isbAffichePersMC1() + "\n"
                + "affichePersMC2=" + isbAffichePersMC2() + "\n"
                + "txtPersLib1=" + getStrPersLib1() + "\n"
                + "txtPersLib2=" + getStrPersLib2() + "\n"
                + "txtPersURL1=" + getStrPersURL1() + "\n"
                + "txtPersURL2=" + getStrPersURL2() + "\n"
                + "nombreImagesFond=" + getiNombreImagesFond() + "\n";
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            strContenuFichier += "<";
            strContenuFichier += "image=" + getImagesFond()[i].getStrFichierImage() + "#";
            strContenuFichier += "posX=" + getImagesFond()[i].getStrPosX() + "#";
            strContenuFichier += "posY=" + getImagesFond()[i].getStrPosY() + "#";
            strContenuFichier += "offsetX=" + getImagesFond()[i].getOffsetX() + "#";
            strContenuFichier += "offsetY=" + getImagesFond()[i].getOffsetY() + "#";
            strContenuFichier += "tailleX=" + getImagesFond()[i].getTailleX() + "#";
            strContenuFichier += "tailleY=" + getImagesFond()[i].getTailleY() + "#";
            strContenuFichier += "opacite=" + getImagesFond()[i].getOpacite() + "#";
            strContenuFichier += "masquable=" + getImagesFond()[i].isMasquable() + "#";
            strContenuFichier += "url=" + getImagesFond()[i].getStrUrl() + "#";
            strContenuFichier += "infobulle=" + getImagesFond()[i].getStrInfobulle() + "#";
            strContenuFichier += ">\n";
        }

        return strContenuFichier;
    }

    /**
     *
     * @param strTemplate
     */
    public void setTemplate(List<String> strTemplate) {
        setbAfficheBoussole(false);
        setbAfficheMasque(false);
        setbAfficheVignettes(false);
        setbAfficheReseauxSociaux(false);
        setiNombreImagesFond(0);
        File fileRepertoirePlan;

        for (String strChaine : strTemplate) {
            if (strChaine.split("image=").length > 1) {
                getImagesFond()[getiNombreImagesFond()] = new ImageFond();

                strChaine = strChaine.replace("<", "");
                strChaine = strChaine.replace(">", "");
                //System.out.println("chaine : " + chaine);
                String[] strElements = strChaine.split("#");
                for (String strTexte1 : strElements) {
                    String strVariable = strTexte1.split("=")[0];
                    String strValeur = "";
                    if (strTexte1.split("=").length > 1) {
                        strValeur = strTexte1.split("=")[1];
                    }
                    //System.out.println(variable + "=" + valeur);
                    switch (strVariable) {
                        case "image":
                            getImagesFond()[getiNombreImagesFond()].setStrFichierImage(strValeur);
                            getImagesFond()[getiNombreImagesFond()].setImgFond(new Image("file:" + strValeur));
                            fileRepertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getImagesFond()[getiNombreImagesFond()].getStrFichierImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "posX":
                            getImagesFond()[getiNombreImagesFond()].setStrPosX(strValeur);
                            break;
                        case "posY":
                            getImagesFond()[getiNombreImagesFond()].setStrPosY(strValeur);
                            break;
                        case "url":
                            getImagesFond()[getiNombreImagesFond()].setStrUrl(strValeur);
                            break;
                        case "infobulle":
                            getImagesFond()[getiNombreImagesFond()].setStrInfobulle(strValeur);
                            break;
                        case "offsetX":
                            getImagesFond()[getiNombreImagesFond()].setOffsetX(Double.parseDouble(strValeur));
                            break;
                        case "offsetY":
                            getImagesFond()[getiNombreImagesFond()].setOffsetY(Double.parseDouble(strValeur));
                            break;
                        case "opacite":
                            getImagesFond()[getiNombreImagesFond()].setOpacite(Double.parseDouble(strValeur));
                            break;
                        case "masquable":
                            getImagesFond()[getiNombreImagesFond()].setMasquable(strValeur.equals("true"));
                            break;
                        case "tailleX":
                            getImagesFond()[getiNombreImagesFond()].setTailleX(Integer.parseInt(strValeur));
                            break;
                        case "tailleY":
                            getImagesFond()[getiNombreImagesFond()].setTailleY(Integer.parseInt(strValeur));
                            break;
                    }

                }
                setiNombreImagesFond(getiNombreImagesFond() + 1);
            } else {
                String strVariable = strChaine.split("=")[0];
                String strValeur = "";
                if (strChaine.split("=").length > 1) {
                    strValeur = strChaine.split("=")[1];
                }
                //System.out.println(variable + "=" + valeur);
                switch (strVariable) {
                    case "couleurTheme":
                        couleurTheme = Color.web(strValeur);
                        break;
                    case "couleurBoutons":
                        couleurBarreClassique = Color.web(strValeur);
                        break;
                    case "couleurHotspots":
                        couleurHotspots = Color.web(strValeur);
                        break;
                    case "couleurHotspotsPhoto":
                        couleurHotspotsPhoto = Color.web(strValeur);
                        break;
                    case "couleurMasque":
                        couleurMasque = Color.web(strValeur);
                        break;

                    case "styleBarre":
                        setStyleBarreClassique(strValeur);
                        break;
                    case "suivantPrecedent":
                        setbSuivantPrecedent(strValeur.equals("true"));
                        break;

                    case "styleHotspots":
                        setStrStyleHotSpots(strValeur);
                        break;
                    case "styleHotspotImages":
                        setStrStyleHotSpotImages(strValeur);
                        break;
                    case "position":
                        setStrPositionBarreClassique(strValeur);

                        break;
                    case "dX":
                        setOffsetXBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "dY":
                        setOffsetYBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "visible":
                        setStrVisibiliteBarreClassique(strValeur);
                        break;
                    case "deplacement":
                        setStrDeplacementsBarreClassique(strValeur);
                        break;
                    case "zoom":
                        setStrZoomBarreClassique(strValeur);
                        break;
                    case "outils":
                        setStrOutilsBarreClassique(strValeur);
                        break;
                    case "rotation":
                        setStrRotationBarreClassique(strValeur);
                        break;
                    case "FS":
                        setStrPleinEcranBarreClassique(strValeur);
                        break;
                    case "souris":
                        setStrSourisBarreClassique(strValeur);
                        break;
                    case "espacementBoutons":
                        setEspacementBarreClassique(Double.parseDouble(strValeur));
                        break;
                    case "bCouleurOrigineBarrePersonnalisee":
                        setbCouleurOrigineBarrePersonnalisee(strValeur.equals("true"));
                        break;
                    case "nombreZonesBarrePersonnalisee":
                        setiNombreZonesBarrePersonnalisee(Integer.parseInt(strValeur));
                        break;
                    case "offsetXBarrePersonnalisee":
                        setOffsetXBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "offsetYBarrePersonnalisee":
                        setOffsetYBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "tailleBarrePersonnalisee":
                        setTailleBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "tailleIconesBarrePersonnalisee":
                        setTailleIconesBarrePersonnalisee(Double.parseDouble(strValeur));
                        break;
                    case "strPositionBarrePersonnalisee":
                        setStrPositionBarrePersonnalisee(strValeur);
                        break;
                    case "strDeplacementsBarrePersonnalisee":
                        setStrDeplacementsBarrePersonnalisee(strValeur);
                        break;
                    case "strZoomBarrePersonnalisee":
                        setStrZoomBarrePersonnalisee(strValeur);
                        break;
                    case "strInfoBarrePersonnalisee":
                        setStrInfoBarrePersonnalisee(strValeur);
                        break;
                    case "strAideBarrePersonnalisee":
                        setStrAideBarrePersonnalisee(strValeur);
                        break;
                    case "strRotationBarrePersonnalisee":
                        setStrRotationBarrePersonnalisee(strValeur);
                        break;
                    case "strPleinEcranBarrePersonnalisee":
                        setStrPleinEcranBarrePersonnalisee(strValeur);
                        break;
                    case "strSourisBarrePersonnalisee":
                        setStrSourisBarrePersonnalisee(strValeur);
                        break;
                    case "strVisibiliteBarrePersonnalisee":
                        setStrVisibiliteBarrePersonnalisee(strValeur);
                        break;
                    case "strLienImageBarrePersonnalisee":
                        setStrLienImageBarrePersonnalisee(strValeur);
                        break;
                    case "strLien1BarrePersonnalisee":
                        setStrLien1BarrePersonnalisee(strValeur);
                        break;
                    case "strLien2BarrePersonnalisee":
                        setStrLien2BarrePersonnalisee(strValeur);
                        break;
                    case "couleurBarrePersonnalisee":
                        couleurBarrePersonnalisee = Color.web(strValeur);
                    case "afficheTitre":
                        setbAfficheTitre(strValeur.equals("true"));
                        break;
                    case "titrePolice":
                        setStrTitrePoliceNom(strValeur);
                        break;
                    case "titrePoliceTaille":
                        setStrTitrePoliceTaille(strValeur);
                        break;
                    case "titreOpacite":
                        setTitreOpacite(Double.parseDouble(strValeur));
                        break;
                    case "titreTaille":
                        setTitreTaille(Double.parseDouble(strValeur));
                        break;
                    case "titreCouleur":
                        setStrCouleurTitre(strValeur);
                        break;
                    case "titreFondCouleur":
                        setStrCouleurFondTitre(strValeur);
                        break;
                    case "bFenetreInfoPersonnalise":
                        setbFenetreInfoPersonnalise(strValeur.equals("true"));
                        break;
                    case "bFenetreAidePersonnalise":
                        setbFenetreAidePersonnalise(strValeur.equals("true"));
                        break;
                    case "strFenetreInfoImage ":
                        setStrFenetreInfoImage(strValeur);
                        if (!strFenetreInfoImage.equals("")) {
                            fileRepertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getStrFenetreInfoImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    case "strFenetreAideImage":
                        setStrFenetreAideImage(strValeur);
                        if (!strFenetreAideImage.equals("")) {
                            fileRepertoirePlan = new File(strRepertTemp + File.separator + "images");
                            if (!fileRepertoirePlan.exists()) {
                                fileRepertoirePlan.mkdirs();
                            }
                            try {
                                copieFichierRepertoire(getStrFenetreAideImage(), fileRepertoirePlan.getAbsolutePath());
                            } catch (IOException ex) {
                                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "fenetreInfoTaille":
                        setFenetreInfoTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAideTaille":
                        setFenetreAideTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoPosX":
                        setFenetreInfoPosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoPosY":
                        setFenetreInfoPosY(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAidePosX":
                        setFenetreAidePosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAidePosY":
                        setFenetreAidePosY(Double.parseDouble(strValeur));
                        break;
                    case "fenetreInfoOpacite":
                        setFenetreInfoOpacite(Double.parseDouble(strValeur));
                        break;
                    case "fenetreAideOpacite":
                        setFenetreAideOpacite(Double.parseDouble(strValeur));
                        break;
                    case "strFenetreURL":
                        setStrFenetreURL(strValeur);
                        break;
                    case "strFenetreTexteURL":
                        setStrFenetreTexteURL(strValeur);
                        break;
                    case "strFenetreURLInfobulle":
                        setStrFenetreURLInfobulle(strValeur);
                        break;
                    case "strFenetreURLCouleur":
                        setStrFenetreURLCouleur(strValeur);
                        break;
                    case "strFenetrePolice":
                        setStrFenetrePolice(strValeur);
                        break;
                    case "fenetrePoliceTaille":
                        setFenetrePoliceTaille(Double.parseDouble(strValeur));
                        break;
                    case "fenetreURLPosX":
                        setFenetreURLPosX(Double.parseDouble(strValeur));
                        break;
                    case "fenetreURLPosY":
                        setFenetreURLPosY(Double.parseDouble(strValeur));
                        break;
                    case "strFenetreCouleurFond":
                        setStrFenetreCouleurFond(strValeur);
                        break;
                    case "fenetreOpaciteFond":
                        setFenetreOpaciteFond(Double.parseDouble(strValeur));
                        break;
                    case "diaporamaOpacite":
                        setDiaporamaOpacite(Double.parseDouble(strValeur));
                        break;
                    case "diaporamaCouleur":
                        setStrCouleurDiaporama(strValeur);
                        break;
                    case "afficheBoussole":
                        setbAfficheBoussole(strValeur.equals("true"));
                        break;
                    case "imageBoussole":
                        setStrImageBoussole(strValeur);
                        break;
                    case "tailleBoussole":
                        setTailleBoussole(Double.parseDouble(strValeur));
                        break;
                    case "positionBoussole":
                        setStrPositionBoussole(strValeur);
                        break;
                    case "dXBoussole":
                        setOffsetXBoussole(Double.parseDouble(strValeur));
                        break;
                    case "dYBoussole":
                        setOffsetYBoussole(Double.parseDouble(strValeur));
                        break;
                    case "opaciteBoussole":
                        setOpaciteBoussole(Double.parseDouble(strValeur));
                        break;
                    case "aiguilleMobile":
                        setbAiguilleMobileBoussole(strValeur.equals("true"));
                        break;
                    case "afficheMasque":
                        setbAfficheMasque(strValeur.equals("true"));
                        break;
                    case "imageMasque":
                        setStrImageMasque(strValeur);
                        break;
                    case "tailleMasque":
                        setTailleMasque(Double.parseDouble(strValeur));
                        break;
                    case "positionMasque":
                        setStrPositionMasque(strValeur);
                        break;
                    case "dXMasque":
                        setdXMasque(Double.parseDouble(strValeur));
                        break;
                    case "dYMasque":
                        setdYMasque(Double.parseDouble(strValeur));
                        break;
                    case "opaciteMasque":
                        setOpaciteMasque(Double.parseDouble(strValeur));
                        break;
                    case "masqueNavigation":
                        setbMasqueNavigation(strValeur.equals("true"));
                        break;
                    case "masqueBoussole":
                        setbMasqueBoussole(strValeur.equals("true"));
                        break;
                    case "masqueTitre":
                        setbMasqueTitre(strValeur.equals("true"));
                        break;
                    case "masquePlan":
                        setbMasquePlan(strValeur.equals("true"));
                        break;
                    case "masqueReseaux":
                        setbMasqueReseaux(strValeur.equals("true"));
                        break;
                    case "masqueVignettes":
                        setbMasqueVignettes(strValeur.equals("true"));
                        break;
                    case "masqueCombo":
                        setbMasqueCombo(strValeur.equals("true"));
                        break;
                    case "masqueSuivPrec":
                        setbMasqueSuivPrec(strValeur.equals("true"));
                        break;
                    case "masqueHotspots":
                        setbMasqueHotspots(strValeur.equals("true"));
                        break;
                    case "afficheReseauxSociaux":
                        setbAfficheReseauxSociaux(strValeur.equals("true"));
                        break;
                    case "tailleReseauxSociaux":
                        setTailleReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "positionReseauxSociaux":
                        setStrPositionReseauxSociaux(strValeur);
                        break;
                    case "dXReseauxSociaux":
                        setdXReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "dYReseauxSociaux":
                        setdYReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "opaciteReseauxSociaux":
                        setOpaciteReseauxSociaux(Double.parseDouble(strValeur));
                        break;
                    case "masqueTwitter":
                        setbReseauxSociauxTwitter(strValeur.equals("true"));
                        break;
                    case "masqueGoogle":
                        setbReseauxSociauxGoogle(strValeur.equals("true"));
                        break;
                    case "masqueFacebook":
                        setbReseauxSociauxFacebook(strValeur.equals("true"));
                        break;
                    case "masqueEmail":
                        setbReseauxSociauxEmail(strValeur.equals("true"));
                        break;
                    case "afficheVignettes":
                        setbAfficheVignettes(strValeur.equals("true"));
                        break;
                    case "positionVignettes":
                        setStrPositionVignettes(strValeur);
                        break;
                    case "opaciteVignettes":
                        setOpaciteVignettes(Double.parseDouble(strValeur));
                        break;
                    case "tailleImageVignettes":
                        setTailleImageVignettes(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondVignettes":
                        setStrCouleurFondVignettes(strValeur);
                        break;
                    case "couleurTexteVignettes":
                        setStrCouleurTexteVignettes(strValeur);
                        break;
                    case "bAfficheComboMenu":
                        setbAfficheComboMenu(strValeur.equals("true"));
                        break;
                    case "bAfficheComboMenuImages":
                        setbAfficheComboMenuImages(strValeur.equals("true"));
                        break;
                    case "positionXComboMenu":
                        setStrPositionXComboMenu(strValeur);
                        break;
                    case "positionYComboMenu":
                        setStrPositionYComboMenu(strValeur);
                        break;
                    case "offsetXComboMenu":
                        setOffsetXComboMenu(Double.parseDouble(strValeur));
                        break;
                    case "offsetYComboMenu":
                        setOffsetYComboMenu(Double.parseDouble(strValeur));
                        break;
                    case "affichePlan":
                        setbAffichePlan(strValeur.equals("true"));
                        break;
                    case "positionPlan":
                        setStrPositionPlan(strValeur);
                        break;
                    case "opacitePlan":
                        setOpacitePlan(Double.parseDouble(strValeur));
                        break;
                    case "largeurPlan":
                        setLargeurPlan(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondPlan":
                        setStrCouleurFondPlan(strValeur);
                        setCouleurFondPlan(Color.valueOf(getStrCouleurFondPlan()));
                        break;
                    case "couleurTextePlan":
                        setStrCouleurTextePlan(strValeur);
                        setCouleurTextePlan(Color.valueOf(getStrCouleurTextePlan()));
                        break;
                    case "afficheRadar":
                        setbAfficheRadar(strValeur.equals("true"));
                        break;
                    case "opaciteRadar":
                        setOpaciteRadar(Double.parseDouble(strValeur));
                        break;
                    case "tailleRadar":
                        setTailleRadar(Double.parseDouble(strValeur));
                        break;
                    case "couleurFondRadar":
                        setStrCouleurFondRadar(strValeur);
                        setCouleurFondRadar(Color.valueOf(getStrCouleurFondRadar()));
                        break;
                    case "couleurLigneRadar":
                        setStrCouleurLigneRadar(strValeur);
                        setCouleurLigneRadar(Color.valueOf(getStrCouleurLigneRadar()));
                        break;
                    case "afficheMenuContextuel":
                        setbAfficheMenuContextuel(strValeur.equals("true"));
                        break;
                    case "affichePrecSuivMC":
                        setbAffichePrecSuivMC(strValeur.equals("true"));
                        break;
                    case "affichePlaneteNormalMC":
                        setbAffichePlanetNormalMC(strValeur.equals("true"));
                        break;
                    case "affichePersMC1":
                        setbAffichePersMC1(strValeur.equals("true"));
                        break;
                    case "affichePersMC2":
                        setbAffichePersMC2(strValeur.equals("true"));
                        break;
                    case "txtPersLib1":
                        setStrPersLib1(strValeur);
                        break;
                    case "txtPersLib2":
                        setStrPersLib2(strValeur);
                        break;
                    case "txtPersURL1":
                        setStrPersURL1(strValeur);
                        break;
                    case "txtPersURL2":
                        setStrPersURL2(strValeur);
                        break;

                }
            }
        }
    }

    public void afficheTemplate() throws IOException {
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().addAll(rbClair, rbSombre, rbPerso, cbImage, ivVisualisation, lblTxtTitre, imgBoussole, imgAiguille, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, apVisuPlan, ivMasque, apAfficheDiapo, apFenetreAfficheInfo, lblFenetreURL, ivDiapo, apAfficheBarrePersonnalisee);

        lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        Color couleur = Color.valueOf(getStrCouleurFondTitre());
        int iRouge = (int) (couleur.getRed() * 255.d);
        int iBleu = (int) (couleur.getBlue() * 255.d);
        int iVert = (int) (couleur.getGreen() * 255.d);
        String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";
        lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond);
        double taille = (double) getTitreTaille() / 100.d * ivVisualisation.getFitWidth();
        lblTxtTitre.setMinWidth(taille);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getMinWidth()) / 2);
        Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte1);
        lblTxtTitre.setPrefHeight(-1);
        lblTxtTitre.setVisible(isbAfficheTitre());
        cbAfficheTitre.setSelected(isbAfficheTitre());
        cpCouleurTitre.setValue(Color.valueOf(getStrCouleurTitre()));
        cpCouleurFondTitre.setValue(Color.valueOf(getStrCouleurFondTitre()));
        cpCouleurBarreClassique.setValue(couleurBarreClassique);
        cpCouleurTheme.setValue(couleurTheme);
        cpCouleurMasques.setValue(couleurMasque);
        cpCouleurHotspots.setValue(couleurHotspots);
        cpCouleurHotspotsPhoto.setValue(couleurHotspotsPhoto);
        cbListePolices.setValue(getStrTitrePoliceNom());
        slOpacite.setValue(getTitreOpacite());
        cblisteStyleBarreClassique.setValue(getStyleBarreClassique());
        bdfOffsetXBarreClassique.setNumber(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetYBarreClassique.setNumber(new BigDecimal(getOffsetYBarreClassique()));
        if (getStrVisibiliteBarreClassique().equals("oui")) {
            cbBarreClassiqueVisible.setSelected(true);
        } else {
            cbBarreClassiqueVisible.setSelected(false);
        }
        if (getStrDeplacementsBarreClassique().equals("oui")) {
            cbDeplacementsBarreClassique.setSelected(true);
        } else {
            cbDeplacementsBarreClassique.setSelected(false);
        }
        if (getStrZoomBarreClassique().equals("oui")) {
            cbZoomBarreClassique.setSelected(true);
        } else {
            cbZoomBarreClassique.setSelected(false);
        }
        if (getStrOutilsBarreClassique().equals("oui")) {
            cbOutilsBarreClassique.setSelected(true);
        } else {
            cbOutilsBarreClassique.setSelected(false);
        }
        switch (getStrPositionBarreClassique()) {
            case "top:left":
                rbTopLeftBarreClassique.setSelected(true);
                break;
            case "top:center":
                rbTopCenterBarreClassique.setSelected(true);
                break;
            case "top:right":
                rbTopRightBarreClassique.setSelected(true);
                break;
            case "middle:left":
                rbMiddleLeftBarreClassique.setSelected(true);
                break;
            case "middle:right":
                rbMiddleRightBarreClassique.setSelected(true);
                break;
            case "bottom:left":
                rbBottomLeftBarreClassique.setSelected(true);
                break;
            case "bottom:center":
                rbBottomCenterBarreClassique.setSelected(true);
                break;
            case "bottom:right":
                rbBottomRightBarreClassique.setSelected(true);
                break;
        }

        if (getStrRotationBarreClassique().equals("oui")) {
            cbRotationBarreClassique.setSelected(true);
        } else {
            cbRotationBarreClassique.setSelected(false);
        }
        if (getStrPleinEcranBarreClassique().equals("oui")) {
            cbFSBarreClassique.setSelected(true);
        } else {
            cbFSBarreClassique.setSelected(false);
        }
        if (getStrSourisBarreClassique().equals("oui")) {
            cbSourisBarreClassique.setSelected(true);
        } else {
            cbSourisBarreClassique.setSelected(false);
        }
        if (getStrVisibiliteBarrePersonnalisee().equals("oui")) {
            chargeBarrePersonnalisee(getStrLienImageBarrePersonnalisee());

            rbCouleurOrigineBarrePersonnalisee.setSelected(isbCouleurOrigineBarrePersonnalisee());
            rbCouleurPersBarrePersonnalisee.setSelected(!isbCouleurOrigineBarrePersonnalisee());

            bdfOffsetXBarrePersonnalisee.setNumber(new BigDecimal(getOffsetXBarrePersonnalisee()));
            bdfOffsetYBarrePersonnalisee.setNumber(new BigDecimal(getOffsetYBarrePersonnalisee()));
            sltailleBarrePersonnalisee.setValue(getTailleBarrePersonnalisee());
            sltailleIconesBarrePersonnalisee.setValue(getTailleIconesBarrePersonnalisee());
            switch (getStrPositionBarrePersonnalisee()) {
                case "top:left":
                    rbTopLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "top:center":
                    rbTopCenterBarrePersonnalisee.setSelected(true);
                    break;
                case "top:right":
                    rbTopRightBarrePersonnalisee.setSelected(true);
                    break;
                case "middle:left":
                    rbMiddleLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "middle:right":
                    rbMiddleRightBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:left":
                    rbBottomLeftBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:center":
                    rbBottomCenterBarrePersonnalisee.setSelected(true);
                    break;
                case "bottom:right":
                    rbBottomRightBarrePersonnalisee.setSelected(true);
                    break;

            }
            cbDeplacementsBarrePersonnalisee.setSelected(getStrDeplacementsBarrePersonnalisee().equals("oui"));
            cbZoomBarrePersonnalisee.setSelected(getStrZoomBarrePersonnalisee().equals("oui"));
            cbRotationBarrePersonnalisee.setSelected(getStrRotationBarrePersonnalisee().equals("oui"));
            cbFSBarrePersonnalisee.setSelected(getStrPleinEcranBarrePersonnalisee().equals("oui"));
            cbSourisBarrePersonnalisee.setSelected(getStrSourisBarrePersonnalisee().equals("oui"));
            cbBarrePersonnaliseeVisible.setSelected(getStrVisibiliteBarrePersonnalisee().equals("oui"));
            cpCouleurBarrePersonnalisee.setValue(couleurBarrePersonnalisee);
            tfLienImageBarrePersonnalisee.setText(getStrLienImageBarrePersonnalisee());
            tfLien1BarrePersonnalisee.setText(getStrLien1BarrePersonnalisee());
            tfLien2BarrePersonnalisee.setText(getStrLien2BarrePersonnalisee());
        }
        cbFenetreInfoPersonnalise.setSelected(isbFenetreInfoPersonnalise());
        cbFenetreAidePersonnalise.setSelected(isbFenetreAidePersonnalise());
        tfFenetreInfoImage.setText(getStrFenetreInfoImage());
        tfFenetreAideImage.setText(getStrFenetreAideImage());
        slFenetreInfoTaille.setValue(getFenetreInfoTaille());
        slFenetreAideTaille.setValue(getFenetreAideTaille());
        bdfFenetreInfoPosX.setNumber(new BigDecimal(getFenetreInfoPosX()));
        bdfFenetreInfoPosY.setNumber(new BigDecimal(getFenetreInfoPosY()));
        bdfFenetreAidePosX.setNumber(new BigDecimal(getFenetreAidePosX()));
        bdfFenetreAidePosY.setNumber(new BigDecimal(getFenetreAidePosY()));
        slFenetreInfoOpacite.setValue(getFenetreInfoOpacite());
        slFenetreAideOpacite.setValue(getFenetreAideOpacite());
        tfFenetreTexteURL.setText(getStrFenetreTexteURL());
        tfFenetreURL.setText(getStrFenetreURL());
        slFenetrePoliceTaille.setValue(getFenetrePoliceTaille());
        bdfFenetreURLPosX.setNumber(new BigDecimal(getFenetreURLPosX()));
        bdfFenetreURLPosY.setNumber(new BigDecimal(getFenetreURLPosY()));
        bdfFenetreURLPosX.setNumber(new BigDecimal(getFenetreURLPosX()));
        bdfFenetreURLPosY.setNumber(new BigDecimal(getFenetreURLPosY()));
        cpFenetreURLCouleur.setValue(Color.valueOf(getStrFenetreURLCouleur()));
        cbSuivantPrecedent.setSelected(isbSuivantPrecedent());
        vbFondSuivant.setVisible(isbSuivantPrecedent());
        vbFondPrecedent.setVisible(isbSuivantPrecedent());
        slTaillePolice.setValue(Double.parseDouble(getStrTitrePoliceTaille()));
        slEspacementBarreClassique.setValue(getEspacementBarreClassique());
        slTaille.setValue(getTitreTaille());
        apVisualisation.getChildren().remove(hbbarreBoutons);
        apVisualisation.getChildren().remove(ivHotSpot);
        apVisualisation.getChildren().remove(ivHotSpotImage);
        slTailleBoussole.setValue(getTailleBoussole());
        slOpaciteBoussole.setValue(getOpaciteBoussole());
        bdfOffsetXBoussole.setNumber(new BigDecimal(getOffsetXBoussole()));
        bdfOffsetYBoussole.setNumber(new BigDecimal(getOffsetYBoussole()));
        cbAiguilleMobile.setSelected(isbAiguilleMobileBoussole());
        cbAfficheBoussole.setSelected(isbAfficheBoussole());
        rbBoussTopLeft.setSelected(getStrPositionBoussole().equals("top:left"));
        rbBoussBottomLeft.setSelected(getStrPositionBoussole().equals("bottom:left"));
        rbBoussTopRight.setSelected(getStrPositionBoussole().equals("top:right"));
        rbBoussBottomRight.setSelected(getStrPositionBoussole().equals("bottom:right"));
        slTailleMasque.setValue(getTailleMasque());
        slOpaciteMasque.setValue(getOpaciteMasque());
        bdfOffsetXMasque.setNumber(new BigDecimal(getdXMasque()));
        bdfOffsetYMasque.setNumber(new BigDecimal(getdYMasque()));
        cbMasqueNavigation.setSelected(isbMasqueNavigation());
        cbMasqueBoussole.setSelected(isbMasqueBoussole());
        cbMasqueTitre.setSelected(isbMasqueTitre());
        cbMasquePlan.setSelected(isbMasquePlan());
        cbMasqueReseaux.setSelected(isbMasqueReseaux());
        cbMasqueVignettes.setSelected(isbMasqueVignettes());
        cbMasqueCombo.setSelected(isbMasqueCombo());
        cbMasqueSuivPrec.setSelected(isbMasqueSuivPrec());
        cbMasqueHotspots.setSelected(isbMasqueHotspots());
        cbAfficheMasque.setSelected(isbAfficheMasque());
        rbMasqueTopLeft.setSelected(getStrPositionMasque().equals("top:left"));
        rbMasqueBottomLeft.setSelected(getStrPositionMasque().equals("bottom:left"));
        rbMasqueTopRight.setSelected(getStrPositionMasque().equals("top:right"));
        rbMasqueBottomRight.setSelected(getStrPositionMasque().equals("bottom:right"));
        slTailleReseauxSociaux.setValue(getTailleReseauxSociaux());
        slOpaciteReseauxSociaux.setValue(getOpaciteReseauxSociaux());
        bdfOffsetXReseauxSociaux.setNumber(new BigDecimal(getdXReseauxSociaux()));
        bdfOffsetYreseauxSociaux.setNumber(new BigDecimal(getdYReseauxSociaux()));
        cbReseauxSociauxTwitter.setSelected(isbReseauxSociauxTwitter());
        cbReseauxSociauxGoogle.setSelected(isbReseauxSociauxGoogle());
        cbReseauxSociauxFacebook.setSelected(isbReseauxSociauxFacebook());
        cbReseauxSociauxEmail.setSelected(isbReseauxSociauxEmail());
        cbAfficheReseauxSociaux.setSelected(isbAfficheReseauxSociaux());
        rbReseauxSociauxTopLeft.setSelected(getStrPositionReseauxSociaux().equals("top:left"));
        rbReseauxSociauxBottomLeft.setSelected(getStrPositionReseauxSociaux().equals("bottom:left"));
        rbReseauxSociauxTopRight.setSelected(getStrPositionReseauxSociaux().equals("top:right"));
        rbReseauxSociauxBottomRight.setSelected(getStrPositionReseauxSociaux().equals("bottom:right"));
        cbAfficheVignettes.setSelected(isbAfficheVignettes());
        slOpaciteVignettes.setValue(getOpaciteVignettes());
        slTailleVignettes.setValue(getTailleImageVignettes());
        rbVignettesLeft.setSelected(getStrPositionVignettes().equals("left"));
        rbVignettesRight.setSelected(getStrPositionVignettes().equals("right"));
        rbVignettesBottom.setSelected(getStrPositionVignettes().equals("bottom"));
        cpCouleurFondVignettes.setValue(Color.valueOf(getStrCouleurFondVignettes()));
        cpCouleurTexteVignettes.setValue(Color.valueOf(getStrCouleurTexteVignettes()));
        cbAfficheComboMenu.setSelected(isbAfficheComboMenu());
        cbAfficheComboMenuImages.setSelected(isbAfficheComboMenuImages());
        String strPosit = getStrPositionYComboMenu() + ":" + getStrPositionXComboMenu();
        switch (strPosit) {
            case "top:left":
                rbComboMenuTopLeft.setSelected(true);
                break;
            case "top:center":
                rbComboMenuTopCenter.setSelected(true);
                break;
            case "top:right":
                rbComboMenuTopRight.setSelected(true);
                break;
            case "bottom:left":
                rbComboMenuBottomLeft.setSelected(true);
                break;
            case "bottom:center":
                rbComboMenuBottomCenter.setSelected(true);
                break;
            case "bottom:right":
                rbComboMenuBottomRight.setSelected(true);
                break;
        }
        bdfOffsetXComboMenu.setNumber(new BigDecimal(getOffsetXComboMenu()));
        bdfOffsetYComboMenu.setNumber(new BigDecimal(getOffsetYComboMenu()));
        cbAffichePlan.setSelected(isbAffichePlan());
        slOpacitePlan.setValue(getOpacitePlan());
        slLargeurPlan.setValue(getLargeurPlan());
        rbPlanLeft.setSelected(getStrPositionPlan().equals("left"));
        rbPlanRight.setSelected(getStrPositionPlan().equals("right"));
        cpCouleurFondPlan.setValue(getCouleurFondPlan());
        cpCouleurTextePlan.setValue(getCouleurTextePlan());
        cpCouleurDiaporama.setValue(Color.valueOf(getStrCouleurDiaporama()));
        slOpaciteDiaporama.setValue(getDiaporamaOpacite());
        if (isbAffichePlan()) {
            tabPlan.setDisable(!isbAffichePlan());
            mniAffichagePlan.setDisable(!isbAffichePlan());
            ivAjouterPlan.setDisable(!isbAffichePlan());
            mniAjouterPlan.setDisable(!isbAffichePlan());
            if (isbAffichePlan()) {
                ivAjouterPlan.setOpacity(1.0);
            } else {
                ivAjouterPlan.setOpacity(0.3);
            }

        }
        cbAfficheRadar.setSelected(isbAfficheRadar());
        slOpaciteRadar.setValue(getOpaciteRadar());
        slTailleRadar.setValue(getTailleRadar());
        cpCouleurFondRadar.setValue(getCouleurFondRadar());
        cpCouleurLigneRadar.setValue(getCouleurLigneRadar());
        cbAfficheMenuContextuel.setSelected(isbAfficheMenuContextuel());
        cbAffichePrecSuivMC.setSelected(isbAffichePrecSuivMC());
        cbAffichePlanetNormalMC.setSelected(isbAffichePlanetNormalMC());
        cbAffichePersMC1.setSelected(isbAffichePersMC1());
        cbAffichePersMC2.setSelected(isbAffichePersMC2());
        tfPersLib1.setText(getStrPersLib1());
        tfPersLib2.setText(getStrPersLib2());
        tfPersURL1.setText(getStrPersURL1());
        tfPersURL2.setText(getStrPersURL2());
        afficheImagesFondInterface();
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        changeCouleurHSPhoto(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        afficheVignettes();
        affichePlan();
        afficheDiaporama();
        afficheFenetreAide();
        afficheFenetreInfo();
    }

    public void rafraichit() {

        cbImage.getItems().clear();
        if (iNombrePanoramiques > 0) {
            rbPerso.setDisable(false);
            String strImgAffiche = "";
            if (cbImage.getSelectionModel().getSelectedItem() != null) {
                strImgAffiche = cbImage.getSelectionModel().getSelectedItem().toString();
            }
            for (int i = 0; i < iNombrePanoramiques; i++) {
                String strNomImage = panoramiquesProjet[i].getStrNomFichier().substring(
                        panoramiquesProjet[i].getStrNomFichier().lastIndexOf(File.separator) + 1,
                        panoramiquesProjet[i].getStrNomFichier().length()
                );
                cbImage.getItems().add(i, strNomImage);
            }
            cbImage.setValue(strImgAffiche);
        }

        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();
        affichePlan();
        afficheVignettes();
    }

    private void retireImageFond(int iNumImage) {
        for (int i = iNumImage; i < getiNombreImagesFond() - 1; i++) {
            getImagesFond()[i] = getImagesFond()[i + 1];
        }
        setiNombreImagesFond(getiNombreImagesFond() - 1);
        afficheImagesFondInterface();
        //System.out.println("Retire l'image " + numImage + " Nombre Images : " + nombreImagesFond);
    }

    private void afficheImagesFondInterface() {

        apImageFond.getChildren().clear();
        Image imgAjoute = new Image("file:" + strRepertAppli + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (me) -> {
                    ajoutImageFond();
                }
        );

        double hauteurPanel = 280;
        taillePanelImageFond = (getiNombreImagesFond()) * (hauteurPanel + 10) + 60;
        apImageFond.setPrefHeight(taillePanelImageFond);
        for (int i = 0; i < getiNombreImagesFond(); i++) {
            int ij = i;
            AnchorPane apImagesFond = new AnchorPane();
            apImagesFond.setPrefWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMinWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setMaxWidth(vbOutils.getPrefWidth() - 20);
            apImagesFond.setPrefHeight(hauteurPanel);
            apImagesFond.setStyle("-fx-border-color : #666666; -fx-border-width : 1px; -fx-border-style :solid;");
            apImagesFond.setLayoutY(i * (hauteurPanel + 10) + 60);
            Pane paneFond1 = new Pane();
            paneFond1.setCursor(Cursor.HAND);
            ImageView ivAjouteImageFond1 = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/suppr.png", 30, 30, true, true));
            paneFond1.setLayoutX(vbOutils.getPrefWidth() - 50);
            paneFond1.setLayoutY(0);
            Tooltip tltpRetireImageFond = new Tooltip(rbLocalisation.getString("interface.imageFondRetire"));
            tltpRetireImageFond.setStyle(strTooltipStyle);
            Tooltip.install(paneFond1, tltpRetireImageFond);
            paneFond1.getChildren().add(ivAjouteImageFond1);
            paneFond1.setOnMouseClicked(
                    (me) -> {
                        retireImageFond(ij);
                    }
            );
            ImageView ivImageFond = new ImageView(getImagesFond()[i].getImgFond());
            ivImageFond.setPreserveRatio(true);
            if (getImagesFond()[i].getImgFond().getWidth() > getImagesFond()[i].getImgFond().getHeight()) {
                ivImageFond.setFitWidth(100);
            } else {
                ivImageFond.setFitHeight(100);
            }
            ivImageFond.setLayoutX(10);
            ivImageFond.setLayoutY(10);
            ToggleGroup tgPosition = new ToggleGroup();
            RadioButton rbImageFondTopLeft = new RadioButton();
            RadioButton rbImageFondTopCenter = new RadioButton();
            RadioButton rbImageFondTopRight = new RadioButton();
            RadioButton rbImageFondMiddleLeft = new RadioButton();
            RadioButton rbImageFondMiddleCenter = new RadioButton();
            RadioButton rbImageFondMiddleRight = new RadioButton();
            RadioButton rbImageFondBottomLeft = new RadioButton();
            RadioButton rbImageFondBottomCenter = new RadioButton();
            RadioButton rbImageFondBottomRight = new RadioButton();

            rbImageFondTopLeft.setUserData("top:left");
            rbImageFondTopCenter.setUserData("top:center");
            rbImageFondTopRight.setUserData("top:right");
            rbImageFondMiddleLeft.setUserData("middle:left");
            rbImageFondMiddleCenter.setUserData("middle:center");
            rbImageFondMiddleRight.setUserData("middle:right");
            rbImageFondBottomLeft.setUserData("bottom:left");
            rbImageFondBottomCenter.setUserData("bottom:center");
            rbImageFondBottomRight.setUserData("bottom:right");

            rbImageFondTopLeft.setToggleGroup(tgPosition);
            rbImageFondTopCenter.setToggleGroup(tgPosition);
            rbImageFondTopRight.setToggleGroup(tgPosition);
            rbImageFondMiddleLeft.setToggleGroup(tgPosition);
            rbImageFondMiddleCenter.setToggleGroup(tgPosition);
            rbImageFondMiddleRight.setToggleGroup(tgPosition);
            rbImageFondBottomLeft.setToggleGroup(tgPosition);
            rbImageFondBottomCenter.setToggleGroup(tgPosition);
            rbImageFondBottomRight.setToggleGroup(tgPosition);
            String strPosit = getImagesFond()[i].getStrPosY() + ":" + getImagesFond()[i].getStrPosX();
            switch (strPosit) {
                case "top:left":
                    rbImageFondTopLeft.setSelected(true);
                    break;
                case "top:center":
                    rbImageFondTopCenter.setSelected(true);
                    break;
                case "top:right":
                    rbImageFondTopRight.setSelected(true);
                    break;
                case "middle:left":
                    rbImageFondMiddleLeft.setSelected(true);
                    break;
                case "middle:center":
                    rbImageFondMiddleCenter.setSelected(true);
                    break;
                case "middle:right":
                    rbImageFondMiddleRight.setSelected(true);
                    break;
                case "bottom:left":
                    rbImageFondBottomLeft.setSelected(true);
                    break;
                case "bottom:center":
                    rbImageFondBottomCenter.setSelected(true);
                    break;
                case "bottom:right":
                    rbImageFondBottomRight.setSelected(true);
                    break;
            }

            int iPosX = 175;
            int iPosY = 30;

            rbImageFondTopLeft.setLayoutX(iPosX);
            rbImageFondTopCenter.setLayoutX(iPosX + 20);
            rbImageFondTopRight.setLayoutX(iPosX + 40);
            rbImageFondTopLeft.setLayoutY(iPosY);
            rbImageFondTopCenter.setLayoutY(iPosY);
            rbImageFondTopRight.setLayoutY(iPosY);

            rbImageFondMiddleLeft.setLayoutX(iPosX);
            rbImageFondMiddleCenter.setLayoutX(iPosX + 20);
            rbImageFondMiddleRight.setLayoutX(iPosX + 40);
            rbImageFondMiddleLeft.setLayoutY(iPosY + 20);
            rbImageFondMiddleCenter.setLayoutY(iPosY + 20);
            rbImageFondMiddleRight.setLayoutY(iPosY + 20);

            rbImageFondBottomLeft.setLayoutX(iPosX);
            rbImageFondBottomCenter.setLayoutX(iPosX + 20);
            rbImageFondBottomRight.setLayoutX(iPosX + 40);
            rbImageFondBottomLeft.setLayoutY(iPosY + 40);
            rbImageFondBottomCenter.setLayoutY(iPosY + 40);
            rbImageFondBottomRight.setLayoutY(iPosY + 40);
            Label lblPosit = new Label(rbLocalisation.getString("interface.positionImageFond"));
            lblPosit.setLayoutX(150);
            lblPosit.setLayoutY(10);
            Label lblOffsetXImageFond = new Label("dX ");
            lblOffsetXImageFond.setLayoutX(25);
            lblOffsetXImageFond.setLayoutY(125);
            Label lblOffsetYImageFond = new Label("dY ");
            lblOffsetYImageFond.setLayoutX(175);
            lblOffsetYImageFond.setLayoutY(125);
            BigDecimalField bdfOffsetXImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetX()));
            bdfOffsetXImageFond.setLayoutX(50);
            bdfOffsetXImageFond.setLayoutY(120);
            bdfOffsetXImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetXImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetXImageFond.setMaxWidth(100);
            BigDecimalField bdfOffsetYImageFond = new BigDecimalField(new BigDecimal(getImagesFond()[i].getOffsetY()));
            bdfOffsetYImageFond.setLayoutX(200);
            bdfOffsetYImageFond.setLayoutY(120);
            bdfOffsetYImageFond.setMaxValue(new BigDecimal(2000));
            bdfOffsetYImageFond.setMinValue(new BigDecimal(-2000));
            bdfOffsetYImageFond.setMaxWidth(100);
            CheckBox cbMasquableImageFond = new CheckBox(rbLocalisation.getString("interface.masquableImageFond"));
            cbMasquableImageFond.setLayoutX(150);
            cbMasquableImageFond.setLayoutY(90);
            cbMasquableImageFond.setSelected(getImagesFond()[i].isMasquable());

            Label lblOpaciteImageFond = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
            lblOpaciteImageFond.setLayoutX(10);
            lblOpaciteImageFond.setLayoutY(160);
            Slider slOpaciteImageFond = new Slider(0, 1.0, getImagesFond()[i].getOpacite());
            slOpaciteImageFond.setLayoutX(120);
            slOpaciteImageFond.setLayoutY(160);
            Label lblTailleImageFond = new Label(rbLocalisation.getString("interface.tailleVignettes"));
            lblTailleImageFond.setLayoutX(10);
            lblTailleImageFond.setLayoutY(190);
            double echelle = getImagesFond()[i].getTailleX() / getImagesFond()[i].getImgFond().getWidth();
            Slider slTailleImageFond = new Slider(0.1, 2.0, echelle);
            slTailleImageFond.setLayoutX(120);
            slTailleImageFond.setLayoutY(190);
            Label lblUrlImageFond = new Label("url");
            lblUrlImageFond.setLayoutX(10);
            lblUrlImageFond.setLayoutY(222);
            TextField tfUrlImageFond = new TextField(getImagesFond()[i].getStrUrl());
            tfUrlImageFond.setPrefHeight(20);
            tfUrlImageFond.setPrefWidth(200);
            tfUrlImageFond.setLayoutX(120);
            tfUrlImageFond.setLayoutY(220);
            Label lblInfobulleImageFond = new Label(rbLocalisation.getString("interface.infobulle"));
            lblInfobulleImageFond.setLayoutX(10);
            lblInfobulleImageFond.setLayoutY(252);
            TextField tfInfobulleImageFond = new TextField(getImagesFond()[i].getStrInfobulle());
            tfInfobulleImageFond.setPrefHeight(20);
            tfInfobulleImageFond.setPrefWidth(200);
            tfInfobulleImageFond.setLayoutX(120);
            tfInfobulleImageFond.setLayoutY(250);

            apImagesFond.getChildren().addAll(ivImageFond, paneFond1,
                    lblPosit,
                    rbImageFondTopLeft, rbImageFondTopCenter, rbImageFondTopRight,
                    rbImageFondMiddleLeft, rbImageFondMiddleCenter, rbImageFondMiddleRight,
                    rbImageFondBottomLeft, rbImageFondBottomCenter, rbImageFondBottomRight,
                    cbMasquableImageFond,
                    lblOffsetXImageFond, bdfOffsetXImageFond, lblOffsetYImageFond, bdfOffsetYImageFond,
                    lblOpaciteImageFond, slOpaciteImageFond,
                    lblTailleImageFond, slTailleImageFond,
                    lblUrlImageFond, tfUrlImageFond,
                    lblInfobulleImageFond, tfInfobulleImageFond
            );

            tgPosition.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                if (tgPosition.getSelectedToggle() != null) {
                    String positImageFond = tgPosition.getSelectedToggle().getUserData().toString();
                    getImagesFond()[ij].setStrPosX(positImageFond.split(":")[1]);
                    getImagesFond()[ij].setStrPosY(positImageFond.split(":")[0]);
                    //System.out.println("Image n° " + j + " position : " + positImageFond);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            bdfOffsetXImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                getImagesFond()[ij].setOffsetX(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });
            bdfOffsetYImageFond.numberProperty().addListener((ov, old_value, new_value) -> {
                getImagesFond()[ij].setOffsetY(new_value.doubleValue());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });
            slOpaciteImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue != null) {
                    double opac = (double) newValue;
                    getImagesFond()[ij].setOpacite(opac);
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            slTailleImageFond.valueProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue != null) {
                    double taille = (double) newValue;
                    getImagesFond()[ij].setTailleX((int) (getImagesFond()[ij].getImgFond().getWidth() * taille));
                    getImagesFond()[ij].setTailleY((int) (getImagesFond()[ij].getImgFond().getHeight() * taille));
                    afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                }
            });
            tfUrlImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                String txt = newValue;
                getImagesFond()[ij].setStrUrl(txt);
            });
            tfInfobulleImageFond.textProperty().addListener((ov, oldValue, newValue) -> {
                String txt = newValue;
                getImagesFond()[ij].setStrInfobulle(txt);
            });
            cbMasquableImageFond.selectedProperty().addListener((ov, old_val, new_val) -> {
                if (new_val != null) {
                    getImagesFond()[ij].setMasquable(new_val);
                }
            });

            apImageFond.getChildren().add(apImagesFond);
        }
        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
    }

    private String ajoutFenetreImage() {
        File fileRepert;
        if (strRepertImagesFond.equals("")) {
            fileRepert = new File(strCurrentDir + File.separator);
        } else {
            fileRepert = new File(strRepertImagesFond);
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

        fileChooser.setInitialDirectory(fileRepert);
        fileChooser.getExtensionFilters().addAll(extFilterImages);

        File fichierImage = fileChooser.showOpenDialog(null);
        if (fichierImage != null) {
            strRepertImagesFond = fichierImage.getParent();
            File fileRepertImage = new File(strRepertTemp + File.separator + "images");
            if (!fileRepertImage.exists()) {
                fileRepertImage.mkdirs();
            }
            try {
                copieFichierRepertoire(fichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
            }

            return fichierImage.getAbsolutePath();
        } else {
            return "";
        }

    }

    private void ajoutImageFond() {
        if (getiNombreImagesFond() < 20) {
            File fileRepert;
            if (strRepertImagesFond.equals("")) {
                fileRepert = new File(strCurrentDir + File.separator);
            } else {
                fileRepert = new File(strRepertImagesFond);
            }

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilterImages = new FileChooser.ExtensionFilter("Fichiers Images (jpg, bmp, png, gif)", "*.jpg", "*.bmp", "*.png", "*.gif");

            fileChooser.setInitialDirectory(fileRepert);
            fileChooser.getExtensionFilters().addAll(extFilterImages);

            File fileFichierImage = fileChooser.showOpenDialog(null);
            if (fileFichierImage != null) {
                strRepertImagesFond = fileFichierImage.getParent();
                File fileRepertImage = new File(strRepertTemp + File.separator + "images");
                if (!fileRepertImage.exists()) {
                    fileRepertImage.mkdirs();
                }
                try {
                    copieFichierRepertoire(fileFichierImage.getAbsolutePath(), fileRepertImage.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EditeurPanovisu.class.getName()).log(Level.SEVERE, null, ex);
                }

                getImagesFond()[getiNombreImagesFond()] = new ImageFond();
                getImagesFond()[getiNombreImagesFond()].setStrFichierImage(fileFichierImage.getAbsolutePath());
                Image imgFond = new Image("file:" + fileFichierImage.getAbsolutePath());
                getImagesFond()[getiNombreImagesFond()].setImgFond(imgFond);
                getImagesFond()[getiNombreImagesFond()].setTailleX((int) imgFond.getWidth());
                getImagesFond()[getiNombreImagesFond()].setTailleY((int) imgFond.getHeight());
                //System.out.println("Ajoute ImageFond Taille " + imagesFond[nombreImagesFond].getTailleX()
                // + "x" + imagesFond[nombreImagesFond].getTailleY());
                setiNombreImagesFond(getiNombreImagesFond() + 1);
                afficheImagesFondInterface();
            }
        }
    }

    /**
     *
     * @param iLargeur
     * @param iHauteur
     */
    public void creeInterface(int iLargeur, int iHauteur) {
        List<String> strLstPolices = new ArrayList<>();
        strLstPolices.add("Arial");
        strLstPolices.add("Arial Black");
        strLstPolices.add("Comic Sans MS");
        strLstPolices.add("Couurier New");
        strLstPolices.add("Lucida Sans Typewriter");
        strLstPolices.add("Segoe Print");
        strLstPolices.add("Tahoma");
        strLstPolices.add("Times New Roman");
        strLstPolices.add("Verdana");
        ObservableList<String> strListePolices = FXCollections.observableList(strLstPolices);
        rbLocalisation = ResourceBundle.getBundle("editeurpanovisu.i18n.PanoVisu", EditeurPanovisu.locale);
        strRepertBoutonsPrincipal = strRepertAppli + File.separator + "theme/barreNavigation";
        strRepertHotSpots = strRepertAppli + File.separator + "theme/hotspots";
        strRepertHotSpotsPhoto = strRepertAppli + File.separator + "theme/photos";
        strRepertBoussoles = strRepertAppli + File.separator + "theme/boussoles";
        strRepertMasques = strRepertAppli + File.separator + "theme/MA";
        strRepertReseauxSociaux = strRepertAppli + File.separator + "theme/reseaux";
        chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
        ArrayList<String> strListeStyles = strListerStyle(strRepertBoutonsPrincipal);
        ArrayList<String> strListeHotSpots = strListerHotSpots(strRepertHotSpots);
        ArrayList<String> strListeHotSpotsPhoto = strListerHotSpots(strRepertHotSpotsPhoto);
        ArrayList<String> strListeBoussoles = strListerBoussoles(strRepertBoussoles);
        ArrayList<String> strListeMasques = strListerMasques(strRepertMasques);
        int iNombreHotSpots = strListeHotSpots.size();
        ImageView[] ivHotspots = new ImageView[iNombreHotSpots];
        int iNombreHotSpotsPhoto = strListeHotSpotsPhoto.size();
        ImageView[] ivHotspotsPhoto = new ImageView[iNombreHotSpotsPhoto];
        imgClaire = new Image("file:" + strRepertAppli + File.separator + "images/claire.jpg");
        imgSombre = new Image("file:" + strRepertAppli + File.separator + "images/sombre.jpg");
        imgSuivant = new ImageView(new Image("file:" + strRepertAppli + File.separator + "panovisu/images/suivant.png"));
        imgPrecedent = new ImageView(new Image("file:" + strRepertAppli + File.separator + "panovisu/images/precedent.png"));
        vbFondSuivant = new VBox(imgSuivant);
        vbFondPrecedent = new VBox(imgPrecedent);
        vbFondSuivant.setStyle("-fx-background-color : black;");
        vbFondSuivant.setOpacity(0.5);
        vbFondPrecedent.setStyle("-fx-background-color : black;");
        vbFondPrecedent.setOpacity(0.5);

        lblTxtTitre = new Label(rbLocalisation.getString("interface.titre"));
        Font fonte = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
        lblTxtTitre.setFont(fonte);
        double largeurOutils = 380;

        paneTabInterface = new Pane();
        hbInterface = new HBox();
        hbInterface.setPrefWidth(iLargeur);
        hbInterface.setPrefHeight(iHauteur);
        paneTabInterface.getChildren().add(hbInterface);
        apVisualisation = new AnchorPane();
        apVisualisation.setPrefWidth(iLargeur - largeurOutils);
        apVisualisation.setMaxWidth(iLargeur - largeurOutils);
        apVisualisation.setMinWidth(iLargeur - largeurOutils);
        apVisualisation.setPrefHeight(iHauteur);
        vbOutils = new VBox();
        ScrollPane spOutils = new ScrollPane(vbOutils);
        spOutils.setId("spOutils");
        spOutils.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spOutils.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spOutils.setMaxHeight(iHauteur - 100);
        spOutils.setFitToWidth(true);
        spOutils.setFitToHeight(true);
        spOutils.setPrefWidth(largeurOutils);
        spOutils.setMaxWidth(largeurOutils);
        spOutils.setTranslateY(15);
        vbOutils.setPrefWidth(largeurOutils - 20);
        vbOutils.setMaxWidth(largeurOutils - 20);
        //vbOutils.setMinHeight(height);
//        vbOutils.setStyle("-fx-background-color : #ccc;");
        hbInterface.getChildren().addAll(apVisualisation, spOutils);
        /*
         * ***************************************************************
         *     Panneau de visualisation de l'interface
         * ***************************************************************
         */
        double tailleMax = apVisualisation.getPrefWidth() - 40;
        if (tailleMax > 1200) {
            tailleMax = 1200;
        }
        ivVisualisation = new ImageView(imgClaire);
        ivVisualisation.setFitWidth(tailleMax);
        if (tailleMax * 2.d / 3.d > iHauteur - 100) {
            ivVisualisation.setFitHeight(iHauteur - 100);
        } else {
            ivVisualisation.setFitHeight(tailleMax * 2.d / 3.d);
        }
        ivVisualisation.setSmooth(true);
        double LX = (apVisualisation.getPrefWidth() - ivVisualisation.getFitWidth()) / 2;
        ivVisualisation.setLayoutX(LX);
        ivVisualisation.setLayoutY(20);
        lblTxtTitre.setMinSize(tailleMax, 30);
        lblTxtTitre.setPadding(new Insets(5));
        lblTxtTitre.setStyle("-fx-background-color : #000;-fx-border-radius: 5px;");
        lblTxtTitre.setAlignment(Pos.CENTER);
        lblTxtTitre.setOpacity(getTitreOpacite());
        lblTxtTitre.setTextFill(Color.WHITE);
        lblTxtTitre.setLayoutY(20);
        lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getMinWidth()) / 2);
        rbClair = new RadioButton("Image claire");
        rbSombre = new RadioButton("Image Sombre");
        rbPerso = new RadioButton("");
        cbImage = new ComboBox();
        cbImage.setDisable(true);
        double positRB = ivVisualisation.getFitHeight() + 30;
        rbClair.setToggleGroup(tgImage);
        rbSombre.setToggleGroup(tgImage);
        rbPerso.setToggleGroup(tgImage);
        apVisualisation.getChildren().addAll(rbClair, rbSombre);
        rbClair.setLayoutX(LX + 40);
        rbClair.setLayoutY(positRB);
        rbClair.setSelected(true);
        rbSombre.setLayoutX(LX + 180);
        rbSombre.setLayoutY(positRB);
        rbPerso.setLayoutX(LX + 320);
        rbPerso.setLayoutY(positRB);
        cbImage.setLayoutX(LX + 350);
        cbImage.setLayoutY(positRB - 3);
        rbPerso.setDisable(true);
        rbClair.setUserData("claire");
        rbSombre.setUserData("sombre");
        rbPerso.setUserData("perso");
        imgBoussole = new ImageView(new Image("file:" + strRepertBoussoles + File.separator + getStrImageBoussole()));
        imgAiguille = new ImageView("file:" + strRepertBoussoles + File.separator + "aiguille.png");
        apAfficheBarrePersonnalisee = new AnchorPane();
        apAfficheBarrePersonnalisee.setBackground(Background.EMPTY);
        apAfficheBarrePersonnalisee.setLayoutX(ivVisualisation.getLayoutX());
        apAfficheBarrePersonnalisee.setLayoutY(ivVisualisation.getLayoutY());
        ivTwitter = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxTwitter()));
        ivGoogle = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxGoogle()));
        ivFacebook = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxFacebook()));
        ivEmail = new ImageView(new Image("file:" + strRepertReseauxSociaux + File.separator + getStrImageReseauxSociauxEmail()));
        apVisuVignettes = new AnchorPane();
        apVisuPlan = new AnchorPane();
        apVisuComboMenu = new AnchorPane();
        apVisualisation.getChildren().clear();
        apVisualisation.getChildren().add(ivVisualisation);
        apVisualisation.getChildren().addAll(lblTxtTitre, imgBoussole, imgAiguille, ivMasque, ivTwitter, ivGoogle, ivFacebook, ivEmail, apVisuVignettes, apVisuComboMenu, vbFondSuivant, vbFondPrecedent, apAfficheBarrePersonnalisee);
        vbFondPrecedent.setPrefWidth(64);
        vbFondPrecedent.setPrefHeight(64);
        vbFondSuivant.setPrefWidth(64);
        vbFondSuivant.setPrefHeight(64);
        vbFondPrecedent.setMaxWidth(64);
        vbFondPrecedent.setMaxHeight(64);
        vbFondSuivant.setMaxWidth(64);
        vbFondSuivant.setMaxHeight(64);
        vbFondPrecedent.setLayoutX(ivVisualisation.getLayoutX());
        vbFondPrecedent.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - vbFondPrecedent.getPrefHeight()) / 2);
        vbFondSuivant.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - vbFondPrecedent.getPrefWidth()));
        vbFondSuivant.setLayoutY(ivVisualisation.getLayoutY() + (ivVisualisation.getFitHeight() - vbFondSuivant.getPrefHeight()) / 2);
        vbFondSuivant.setVisible(isbSuivantPrecedent());
        vbFondPrecedent.setVisible(isbSuivantPrecedent());
        apAfficheDiapo = new AnchorPane();
        apAfficheDiapo.setPrefWidth(ivVisualisation.getFitWidth());
        apAfficheDiapo.setPrefHeight(ivVisualisation.getFitHeight());
        apAfficheDiapo.setLayoutX(LX);
        apAfficheDiapo.setLayoutY(20);
        apAfficheDiapo.setTranslateZ(10);
        apAfficheDiapo.setVisible(false);
        ivDiapo = new ImageView(new Image("file:" + strRepertAppli + File.separator + "images/testImage.png"));
        ivDiapo.setPreserveRatio(false);
        ivDiapo.setFitHeight(ivVisualisation.getFitHeight() * 0.8);
        ivDiapo.setFitWidth(ivVisualisation.getFitWidth() * 0.8);
        ivDiapo.setLayoutX((ivVisualisation.getFitWidth() - ivDiapo.getFitWidth()) / 2 + LX);
        ivDiapo.setLayoutY((ivVisualisation.getFitHeight() - ivDiapo.getFitHeight()) / 2 + 20);
        ivDiapo.setVisible(false);
        apVisualisation.getChildren().addAll(apAfficheDiapo, ivDiapo);
        afficheBoussole();
        afficheMasque();
        afficheReseauxSociaux();

        afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());

        /*
         * *****************************************
         *     Pannels d'outils 
         * *****************************************
         */
        AnchorPane apCoulTheme = new AnchorPane();
        cpCouleurTheme = new ColorPicker(couleurTheme);
        String strCoul2 = cpCouleurTheme.getValue().toString().substring(2, 8);
        changeCouleurTitre(strCoul2);
        changeCouleurVignettes(strCoul2);
        Label lblCouleurTheme = new Label(rbLocalisation.getString("interface.couleurTheme"));
        lblCouleurTheme.setLayoutX(20);
        lblCouleurTheme.setLayoutY(20);
        cpCouleurTheme.setLayoutX(150);
        cpCouleurTheme.setLayoutY(20);
        apCoulTheme.setPrefHeight(40);
        apCoulTheme.setMinHeight(40);
        apCoulTheme.getChildren().addAll(lblCouleurTheme, cpCouleurTheme);

        AnchorPane apCLASS = new AnchorPane();
        AnchorPane apPERS = new AnchorPane();
        AnchorPane apHS = new AnchorPane();
        AnchorPane apTIT = new AnchorPane();
        AnchorPane apECR = new AnchorPane();
        AnchorPane apDIA = new AnchorPane();
        AnchorPane apBOUSS = new AnchorPane();
        AnchorPane apMASQ = new AnchorPane();
        AnchorPane apRS = new AnchorPane();
        AnchorPane apVIG = new AnchorPane();
        AnchorPane apCBM = new AnchorPane();
        AnchorPane apPLAN = new AnchorPane();
        AnchorPane apIF = new AnchorPane();
        AnchorPane apMC = new AnchorPane();

        /*
         * *****************************************
         *     Panel Titre 
         * ****************************************
         */
        AnchorPane apTitre = new AnchorPane();
        apTitre.setLayoutY(40);
        apTitre.setLayoutX(10);
        apTitre.setPrefHeight(230);
        Label lblPanelTitre = new Label(rbLocalisation.getString("interface.styleTitre"));
        lblPanelTitre.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelTitre.setStyle("-fx-background-color : #666");
        lblPanelTitre.setTextFill(Color.WHITE);
        lblPanelTitre.setPadding(new Insets(5));
        lblPanelTitre.setLayoutX(10);
        lblPanelTitre.setLayoutY(10);
        ImageView ivBtnPlusTitre = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusTitre.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusTitre.setLayoutY(11);
        cbAfficheTitre = new CheckBox(rbLocalisation.getString("interface.afficheTitre"));
        cbAfficheTitre.setSelected(isbAfficheTitre());
        cbAfficheTitre.setLayoutX(10);
        cbAfficheTitre.setLayoutY(15);

        cbListePolices = new ComboBox(strListePolices);
        cbListePolices.setValue(getStrTitrePoliceNom());
        cbListePolices.setLayoutX(180);
        cbListePolices.setLayoutY(42);
        cbListePolices.setMaxWidth(135);

        Label lblChoixPoliceTitre = new Label(rbLocalisation.getString("interface.choixPolice"));
        lblChoixPoliceTitre.setLayoutX(10);
        lblChoixPoliceTitre.setLayoutY(45);
        Label lblChoixTailleTitre = new Label(rbLocalisation.getString("interface.choixTaillePolice"));
        lblChoixTailleTitre.setLayoutX(10);
        lblChoixTailleTitre.setLayoutY(75);
        slTaillePolice = new Slider(8, 72, 12);
        slTaillePolice.setLayoutX(180);
        slTaillePolice.setLayoutY(75);

        Label lblChoixCouleurTitre = new Label(rbLocalisation.getString("interface.choixCouleur"));
        lblChoixCouleurTitre.setLayoutX(10);
        lblChoixCouleurTitre.setLayoutY(105);
        cpCouleurTitre = new ColorPicker(Color.valueOf(getStrCouleurTitre()));
        cpCouleurTitre.setLayoutX(180);
        cpCouleurTitre.setLayoutY(103);
        Label lblChoixCouleurFondTitre = new Label(rbLocalisation.getString("interface.choixCouleurFond"));
        lblChoixCouleurFondTitre.setLayoutX(10);
        lblChoixCouleurFondTitre.setLayoutY(135);
        cpCouleurFondTitre = new ColorPicker(Color.valueOf(getStrCouleurFondTitre()));
        cpCouleurFondTitre.setLayoutX(180);
        cpCouleurFondTitre.setLayoutY(133);

        Label lblChoixOpacite = new Label(rbLocalisation.getString("interface.choixOpaciteTitre"));
        lblChoixOpacite.setLayoutX(10);
        lblChoixOpacite.setLayoutY(165);
        slOpacite = new Slider(0, 1, getTitreOpacite());
        slOpacite.setLayoutX(180);
        slOpacite.setLayoutY(165);
        Label lblChoixTaille = new Label(rbLocalisation.getString("interface.choixTailleTitre"));
        lblChoixTaille.setLayoutX(10);
        lblChoixTaille.setLayoutY(195);
        slTaille = new Slider(0, 100, getTitreTaille());
        slTaille.setLayoutX(180);
        slTaille.setLayoutY(195);

        apTitre.getChildren().addAll(
                cbAfficheTitre,
                lblChoixPoliceTitre, cbListePolices,
                lblChoixTailleTitre, slTaillePolice,
                lblChoixCouleurTitre, cpCouleurTitre,
                lblChoixCouleurFondTitre, cpCouleurFondTitre,
                lblChoixOpacite, slOpacite,
                lblChoixTaille, slTaille);
        double tailleInitialeTitre = apTitre.getPrefHeight();
        apTitre.setPrefHeight(0);
        apTitre.setMaxHeight(0);
        apTitre.setMinHeight(0);
        apTitre.setVisible(false);

        lblPanelTitre.setOnMouseClicked((me) -> {
            if (apTitre.isVisible()) {
                apTitre.setPrefHeight(0);
                apTitre.setMaxHeight(0);
                apTitre.setMinHeight(0);
                apTitre.setVisible(false);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apTitre.setPrefHeight(tailleInitialeTitre);
                apTitre.setMaxHeight(tailleInitialeTitre);
                apTitre.setMinHeight(tailleInitialeTitre);
                apTitre.setVisible(true);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusTitre.setOnMouseClicked((me) -> {
            if (apTitre.isVisible()) {
                apTitre.setPrefHeight(0);
                apTitre.setMaxHeight(0);
                apTitre.setMinHeight(0);
                apTitre.setVisible(false);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apTitre.setPrefHeight(tailleInitialeTitre);
                apTitre.setMaxHeight(tailleInitialeTitre);
                apTitre.setMinHeight(tailleInitialeTitre);
                apTitre.setVisible(true);
                ivBtnPlusTitre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        /*
         * *****************************************
         *     Panel Fenetre 
         * ****************************************
         */
        apFenetreAfficheInfo.setVisible(false);
        lblFenetreURL.setVisible(false);
        //apVisualisation.getChildren().add(apFenetreAfficheInfo);
        AnchorPane apFenetre = new AnchorPane();
        apFenetre.setLayoutY(40);
        apFenetre.setLayoutX(10);
        apFenetre.setPrefHeight(660);
        Label lblPanelFenetre = new Label(rbLocalisation.getString("interface.styleFenetre"));
        lblPanelFenetre.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelFenetre.setStyle("-fx-background-color : #666");
        lblPanelFenetre.setTextFill(Color.WHITE);
        lblPanelFenetre.setPadding(new Insets(5));
        lblPanelFenetre.setLayoutX(10);
        lblPanelFenetre.setLayoutY(10);
        ImageView ivBtnPlusFenetre = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusFenetre.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusFenetre.setLayoutY(11);
        CheckBox cbAfficheFenetreInfo = new CheckBox(rbLocalisation.getString("interface.afficheFenetreInfo"));
        cbAfficheFenetreInfo.setSelected(bAfficheFenetreInfo);
        cbAfficheFenetreInfo.setLayoutX(10);
        cbAfficheFenetreInfo.setLayoutY(15);
        cbFenetreInfoPersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreInfoPersonnalise"));
        cbFenetreInfoPersonnalise.setSelected(isbFenetreInfoPersonnalise());
        cbFenetreInfoPersonnalise.setLayoutX(10);
        cbFenetreInfoPersonnalise.setLayoutY(45);
        AnchorPane apFenetreInfoPers = new AnchorPane();
        apFenetreInfoPers.setLayoutY(75);
        apFenetreInfoPers.setDisable(!isbFenetreInfoPersonnalise());
        Line ligne1 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne1.setStrokeWidth(0.2);

        Label lblFenetreInfoImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreInfoImage.setLayoutX(20);
        lblFenetreInfoImage.setLayoutY(5);
        tfFenetreInfoImage = new TextField();
        tfFenetreInfoImage.setLayoutX(40);
        tfFenetreInfoImage.setLayoutY(25);
        tfFenetreInfoImage.setPrefWidth(250);
        Button btnFenetreInfo = new Button("...");
        btnFenetreInfo.setLayoutX(300);
        btnFenetreInfo.setLayoutY(25);
        Label lblFenetreInfoTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreInfoTaille.setLayoutX(20);
        lblFenetreInfoTaille.setLayoutY(55);
        slFenetreInfoTaille = new Slider(25, 200, getFenetreInfoTaille());
        slFenetreInfoTaille.setLayoutX(140);
        slFenetreInfoTaille.setLayoutY(55);
        Label lblFenetreInfoOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreInfoOpacite.setLayoutX(20);
        lblFenetreInfoOpacite.setLayoutY(85);
        slFenetreInfoOpacite = new Slider(0, 1, getFenetreInfoOpacite());
        slFenetreInfoOpacite.setLayoutX(140);
        slFenetreInfoOpacite.setLayoutY(85);
        Label lblFenetreInfoPosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreInfoPosX.setLayoutX(20);
        lblFenetreInfoPosX.setLayoutY(120);
        bdfFenetreInfoPosX = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosX()));
        bdfFenetreInfoPosX.setLayoutX(100);
        bdfFenetreInfoPosX.setLayoutY(115);
        bdfFenetreInfoPosX.setPrefWidth(60);
        Label lblFenetreInfoPosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreInfoPosY.setLayoutX(180);
        lblFenetreInfoPosY.setLayoutY(120);
        bdfFenetreInfoPosY = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosY()));
        bdfFenetreInfoPosY.setLayoutX(260);
        bdfFenetreInfoPosY.setLayoutY(115);
        bdfFenetreInfoPosY.setPrefWidth(60);
        Label lblFenetreInfoURL = new Label("URL");
        lblFenetreInfoURL.setLayoutX(20);
        lblFenetreInfoURL.setLayoutY(170);
        tfFenetreURL = new TextField();
        tfFenetreURL.setLayoutX(120);
        tfFenetreURL.setLayoutY(165);
        tfFenetreURL.setPrefWidth(210);
        Label lblFenetreInfoTexteURL = new Label(rbLocalisation.getString("interface.fenetreLibelleURL"));
        lblFenetreInfoTexteURL.setLayoutX(20);
        lblFenetreInfoTexteURL.setLayoutY(200);
        tfFenetreTexteURL = new TextField();
        tfFenetreTexteURL.setLayoutX(120);
        tfFenetreTexteURL.setLayoutY(195);
        tfFenetreTexteURL.setPrefWidth(210);

        Label lblFenetrePoliceTaille = new Label(rbLocalisation.getString("interface.fenetrePoliceTaille"));
        lblFenetrePoliceTaille.setLayoutX(20);
        lblFenetrePoliceTaille.setLayoutY(230);
        slFenetrePoliceTaille = new Slider(7, 48, getFenetrePoliceTaille());
        slFenetrePoliceTaille.setLayoutX(140);
        slFenetrePoliceTaille.setLayoutY(230);

        Label lblFenetreURLCouleur = new Label(rbLocalisation.getString("interface.fenetreURLChoixCouleur"));
        lblFenetreURLCouleur.setLayoutX(20);
        lblFenetreURLCouleur.setLayoutY(260);
        cpFenetreURLCouleur = new ColorPicker(Color.valueOf(getStrFenetreURLCouleur()));
        cpFenetreURLCouleur.setLayoutX(200);
        cpFenetreURLCouleur.setLayoutY(256);

        Label lblFenetreURLPosX = new Label(rbLocalisation.getString("interface.fenetrePosX") + " URL");
        lblFenetreURLPosX.setLayoutX(20);
        lblFenetreURLPosX.setLayoutY(295);

        bdfFenetreURLPosX = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosX()));
        bdfFenetreURLPosX.setLayoutX(110);
        bdfFenetreURLPosX.setLayoutY(290);
        bdfFenetreURLPosX.setPrefWidth(60);
        Label lblFenetreURLPosY = new Label(rbLocalisation.getString("interface.fenetrePosY") + " URL");
        lblFenetreURLPosY.setLayoutX(185);
        lblFenetreURLPosY.setLayoutY(295);
        bdfFenetreURLPosY = new BigDecimalField(BigDecimal.valueOf(getFenetreInfoPosY()));
        bdfFenetreURLPosY.setLayoutX(275);
        bdfFenetreURLPosY.setLayoutY(290);
        bdfFenetreURLPosY.setPrefWidth(60);

        Line ligne2 = new Line(0, 320, vbOutils.getPrefWidth(), 320);
        ligne2.setStrokeWidth(0.2);

        apFenetreInfoPers.getChildren().addAll(
                ligne1,
                lblFenetreInfoImage, tfFenetreInfoImage, btnFenetreInfo,
                lblFenetreInfoTaille, slFenetreInfoTaille,
                lblFenetreInfoOpacite, slFenetreInfoOpacite,
                lblFenetreInfoPosX, bdfFenetreInfoPosX,
                lblFenetreInfoPosY, bdfFenetreInfoPosY,
                lblFenetreInfoURL, tfFenetreURL,
                lblFenetreInfoTexteURL, tfFenetreTexteURL,
                lblFenetrePoliceTaille, slFenetrePoliceTaille,
                lblFenetreURLCouleur, cpFenetreURLCouleur,
                lblFenetreURLPosX, bdfFenetreURLPosX,
                lblFenetreURLPosY, bdfFenetreURLPosY,
                ligne2
        );

        CheckBox cbAfficheFenetreAide = new CheckBox(rbLocalisation.getString("interface.afficheFenetreAide"));
        cbAfficheFenetreAide.setSelected(bAfficheFenetreAide);
        cbAfficheFenetreAide.setLayoutX(10);
        cbAfficheFenetreAide.setLayoutY(400);

        cbFenetreAidePersonnalise = new CheckBox(rbLocalisation.getString("interface.fenetreAidePersonnalise"));
        cbFenetreAidePersonnalise.setSelected(isbFenetreAidePersonnalise());
        cbFenetreAidePersonnalise.setLayoutX(10);
        cbFenetreAidePersonnalise.setLayoutY(430);
        AnchorPane apFenetreAidePers = new AnchorPane();
        apFenetreAidePers.setLayoutY(460);
        apFenetreAidePers.setDisable(!isbFenetreAidePersonnalise());
        Line ligne3 = new Line(0, 0, vbOutils.getPrefWidth(), 0);
        ligne3.setStrokeWidth(0.2);

        Label lblFenetreAideImage = new Label(rbLocalisation.getString("interface.fenetreImage"));
        lblFenetreAideImage.setLayoutX(20);
        lblFenetreAideImage.setLayoutY(5);
        tfFenetreAideImage = new TextField();
        tfFenetreAideImage.setLayoutX(40);
        tfFenetreAideImage.setLayoutY(25);
        tfFenetreAideImage.setPrefWidth(250);
        Button btnFenetreAide = new Button("...");
        btnFenetreAide.setLayoutX(300);
        btnFenetreAide.setLayoutY(25);
        Label lblFenetreAideTaille = new Label(rbLocalisation.getString("interface.fenetreTaille"));
        lblFenetreAideTaille.setLayoutX(20);
        lblFenetreAideTaille.setLayoutY(55);
        slFenetreAideTaille = new Slider(50, 200, getFenetreAideTaille());
        slFenetreAideTaille.setLayoutX(140);
        slFenetreAideTaille.setLayoutY(55);
        Label lblFenetreAideOpacite = new Label(rbLocalisation.getString("interface.fenetreOpacite"));
        lblFenetreAideOpacite.setLayoutX(20);
        lblFenetreAideOpacite.setLayoutY(85);
        slFenetreAideOpacite = new Slider(0, 1, getFenetreAideOpacite());
        slFenetreAideOpacite.setLayoutX(140);
        slFenetreAideOpacite.setLayoutY(85);
        Label lblFenetreAidePosX = new Label(rbLocalisation.getString("interface.fenetrePosX"));
        lblFenetreAidePosX.setLayoutX(20);
        lblFenetreAidePosX.setLayoutY(120);
        bdfFenetreAidePosX = new BigDecimalField(BigDecimal.valueOf(getFenetreAidePosX()));
        bdfFenetreAidePosX.setLayoutX(100);
        bdfFenetreAidePosX.setLayoutY(115);
        bdfFenetreAidePosX.setPrefWidth(60);
        Label lblFenetreAidePosY = new Label(rbLocalisation.getString("interface.fenetrePosY"));
        lblFenetreAidePosY.setLayoutX(180);
        lblFenetreAidePosY.setLayoutY(120);
        bdfFenetreAidePosY = new BigDecimalField(BigDecimal.valueOf(getFenetreAidePosY()));
        bdfFenetreAidePosY.setLayoutX(260);
        bdfFenetreAidePosY.setLayoutY(115);
        bdfFenetreAidePosY.setPrefWidth(60);

        Line ligne4 = new Line(0, 150, vbOutils.getPrefWidth(), 150);
        ligne4.setStrokeWidth(0.2);

        apFenetreAidePers.getChildren().addAll(
                ligne3,
                lblFenetreAideImage, tfFenetreAideImage, btnFenetreAide,
                lblFenetreAideTaille, slFenetreAideTaille,
                lblFenetreAideOpacite, slFenetreAideOpacite,
                lblFenetreAidePosX, bdfFenetreAidePosX,
                lblFenetreAidePosY, bdfFenetreAidePosY,
                ligne4
        );

        apFenetre.getChildren().addAll(
                cbAfficheFenetreInfo,
                cbFenetreInfoPersonnalise,
                apFenetreInfoPers,
                cbAfficheFenetreAide,
                cbFenetreAidePersonnalise,
                apFenetreAidePers
        );
        double tailleInitialeFenetre = apFenetre.getPrefHeight();
        apFenetre.setPrefHeight(0);
        apFenetre.setMaxHeight(0);
        apFenetre.setMinHeight(0);
        apFenetre.setVisible(false);

        lblPanelFenetre.setOnMouseClicked((me) -> {
            if (apFenetre.isVisible()) {
                apFenetre.setPrefHeight(0);
                apFenetre.setMaxHeight(0);
                apFenetre.setMinHeight(0);
                apFenetre.setVisible(false);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(false);
            } else {
                apFenetre.setPrefHeight(tailleInitialeFenetre);
                apFenetre.setMaxHeight(tailleInitialeFenetre);
                apFenetre.setMinHeight(tailleInitialeFenetre);
                apFenetre.setVisible(true);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(true);
            }
        });
        ivBtnPlusFenetre.setOnMouseClicked((me) -> {
            if (apFenetre.isVisible()) {
                apFenetre.setPrefHeight(0);
                apFenetre.setMaxHeight(0);
                apFenetre.setMinHeight(0);
                apFenetre.setVisible(false);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(false);
            } else {
                apFenetre.setPrefHeight(tailleInitialeFenetre);
                apFenetre.setMaxHeight(tailleInitialeFenetre);
                apFenetre.setMinHeight(tailleInitialeFenetre);
                apFenetre.setVisible(true);
                ivBtnPlusFenetre.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbAfficheFenetreInfo.setSelected(true);
            }
        });

        /*
         * *****************************************
         *     Panel Diaporama
         * ****************************************
         */
        AnchorPane apDiapo = new AnchorPane();
        apDiapo.setLayoutY(40);
        apDiapo.setLayoutX(10);
        apDiapo.setPrefHeight(100);
        Label lblDiaporama = new Label(rbLocalisation.getString("interface.diaporama"));
        lblDiaporama.setPrefWidth(vbOutils.getPrefWidth());
        lblDiaporama.setStyle("-fx-background-color : #666");
        lblDiaporama.setTextFill(Color.WHITE);
        lblDiaporama.setPadding(new Insets(5));
        lblDiaporama.setLayoutX(10);
        lblDiaporama.setLayoutY(10);
        ImageView ivBtnDiaporama = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnDiaporama.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnDiaporama.setLayoutY(11);
        CheckBox cbVisualiseDiapo = new CheckBox(rbLocalisation.getString("interface.visualiseDiaporama"));
        cbVisualiseDiapo.setLayoutX(10);
        cbVisualiseDiapo.setLayoutY(10);
        cbVisualiseDiapo.setSelected(false);
        Label lblChoixCouleurDiaporama = new Label(rbLocalisation.getString("interface.choixCouleurDiaporama"));
        lblChoixCouleurDiaporama.setLayoutX(10);
        lblChoixCouleurDiaporama.setLayoutY(40);
        cpCouleurDiaporama = new ColorPicker(Color.valueOf(getStrCouleurDiaporama()));
        cpCouleurDiaporama.setLayoutX(180);
        cpCouleurDiaporama.setLayoutY(38);

        Label lblChoixOpaciteDiaporama = new Label(rbLocalisation.getString("interface.choixOpaciteDiaporama"));
        lblChoixOpaciteDiaporama.setLayoutX(10);
        lblChoixOpaciteDiaporama.setLayoutY(70);
        slOpaciteDiaporama = new Slider(0, 1, getDiaporamaOpacite());
        slOpaciteDiaporama.setLayoutX(180);
        slOpaciteDiaporama.setLayoutY(70);

        apDiapo.getChildren().addAll(
                cbVisualiseDiapo,
                lblChoixCouleurDiaporama, cpCouleurDiaporama,
                lblChoixOpaciteDiaporama, slOpaciteDiaporama
        );
        double tailleInitialeDiaporama = apDiapo.getPrefHeight();
        apDiapo.setPrefHeight(0);
        apDiapo.setMaxHeight(0);
        apDiapo.setMinHeight(0);
        apDiapo.setVisible(false);

        lblDiaporama.setOnMouseClicked((me) -> {
            if (apDiapo.isVisible()) {
                apDiapo.setPrefHeight(0);
                apDiapo.setMaxHeight(0);
                apDiapo.setMinHeight(0);
                apDiapo.setVisible(false);
                ivBtnDiaporama.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(false);
            } else {
                apDiapo.setPrefHeight(tailleInitialeDiaporama);
                apDiapo.setMaxHeight(tailleInitialeDiaporama);
                apDiapo.setMinHeight(tailleInitialeDiaporama);
                apDiapo.setVisible(true);
                ivBtnDiaporama.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(true);
            }
        });
        ivBtnDiaporama.setOnMouseClicked((me) -> {
            if (apDiapo.isVisible()) {
                apDiapo.setPrefHeight(0);
                apDiapo.setMaxHeight(0);
                apDiapo.setMinHeight(0);
                apDiapo.setVisible(false);
                ivBtnDiaporama.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(false);

            } else {
                apDiapo.setPrefHeight(tailleInitialeDiaporama);
                apDiapo.setMaxHeight(tailleInitialeDiaporama);
                apDiapo.setMinHeight(tailleInitialeDiaporama);
                apDiapo.setVisible(true);
                ivBtnDiaporama.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                cbVisualiseDiapo.setSelected(true);
            }
        });

        /*
         * *************************************************
         *     Panel HotSpots 
         * *************************************************
         */
        AnchorPane apHotSpots = new AnchorPane();
        ImageView ivBtnPlusHS = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusHS.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusHS.setLayoutY(11);
        Label lblChoixHS = new Label(rbLocalisation.getString("interface.choixHotspot"));
        lblChoixHS.setPrefWidth(vbOutils.getPrefWidth());
        lblChoixHS.setStyle("-fx-background-color : #666");
        lblChoixHS.setTextFill(Color.WHITE);
        lblChoixHS.setPadding(new Insets(5));
        lblChoixHS.setLayoutX(10);
        lblChoixHS.setLayoutY(10);

        double tailleHS = 35.d * ((int) (iNombreHotSpots / 9 + 1) + (int) (iNombreHotSpotsPhoto / 6 + 1)) + 100;
        apHotSpots.setPrefHeight(tailleHS);
        apHotSpots.setLayoutX(50);
        apHotSpots.setLayoutY(40);
        apHotSpots.setStyle("-fx-background-color : #fff");
        apHotSpots.setPadding(new Insets(5));
        int i = 0;
        double xHS;
        double yHS = 25;
        Label lblHSPanoramique = new Label(rbLocalisation.getString("interface.HSPanoramique"));
        lblHSPanoramique.setLayoutY(5);
        lblHSPanoramique.setLayoutX(20);
        Label lblHSPhoto = new Label(rbLocalisation.getString("interface.HSPhoto"));
        lblHSPhoto.setLayoutY((int) (iNombreHotSpots / 6 + 1) * 35 + 45);
        lblHSPhoto.setLayoutX(20);
        apHotSpots.getChildren().addAll(lblHSPanoramique, lblHSPhoto);
        for (String strNomImage : strListeHotSpots) {
            Pane paneFond = new Pane();
            ivHotspots[i] = new ImageView(new Image("file:" + strRepertHotSpots + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol = i % 9;
            int iRow = i / 9;
            xHS = iCol * 40 + 5;
            yHS = iRow * 35 + 25;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setStyle("-fx-background-color : #ccc");
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpot);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                setStrStyleHotSpots(strNomImage);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());

            });
            paneFond.getChildren().add(ivHotspots[i]);
            apHotSpots.getChildren().add(paneFond);
            i++;

        }
        cpCouleurHotspots = new ColorPicker(couleurHotspots);
        Label lblCouleurHotspot = new Label(rbLocalisation.getString("interface.couleurHS"));
        lblCouleurHotspot.setLayoutX(20);
        lblCouleurHotspot.setLayoutY(yHS + 50);
        cpCouleurHotspots.setLayoutX(200);
        cpCouleurHotspots.setLayoutY(yHS + 50);
        apHotSpots.getChildren().addAll(lblCouleurHotspot, cpCouleurHotspots);
        i = 0;
        for (String strNomImage : strListeHotSpotsPhoto) {
            Pane paneFond = new Pane();
            ivHotspotsPhoto[i] = new ImageView(new Image("file:" + strRepertHotSpotsPhoto + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol = i % 9;
            int iRow = i / 9;
            xHS = iCol * 40 + 5;
            yHS = (iRow + (int) (iNombreHotSpots / 9 + 1)) * 35 + 65;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setStyle("-fx-background-color : #ccc");
            paneFond.setOnMouseClicked((me) -> {
                apVisualisation.getChildren().remove(hbbarreBoutons);
                apVisualisation.getChildren().remove(ivHotSpot);
                apVisualisation.getChildren().remove(ivHotSpotImage);
                setStrStyleHotSpotImages(strNomImage);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            });
            paneFond.getChildren().add(ivHotspotsPhoto[i]);
            apHotSpots.getChildren().add(paneFond);
            i++;

        }
        cpCouleurHotspotsPhoto = new ColorPicker(couleurHotspotsPhoto);
        Label lblCouleurHotspotPhoto = new Label(rbLocalisation.getString("interface.couleurHSPhoto"));
        lblCouleurHotspotPhoto.setLayoutX(20);
        lblCouleurHotspotPhoto.setLayoutY(tailleHS - 20);
        cpCouleurHotspotsPhoto.setLayoutX(200);
        cpCouleurHotspotsPhoto.setLayoutY(tailleHS - 20);
        apHotSpots.getChildren().addAll(lblCouleurHotspotPhoto, cpCouleurHotspotsPhoto);
        apHotSpots.setPrefHeight(0);
        apHotSpots.setMaxHeight(0);
        apHotSpots.setMinHeight(0);
        apHotSpots.setVisible(false);

        lblChoixHS.setOnMouseClicked((me) -> {
            if (apHotSpots.isVisible()) {
                ivBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(0);
                apHotSpots.setMaxHeight(0);
                apHotSpots.setMinHeight(0);
                apHotSpots.setVisible(false);
            } else {
                ivBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(tailleHS);
                apHotSpots.setMaxHeight(tailleHS);
                apHotSpots.setMinHeight(tailleHS);
                apHotSpots.setVisible(true);
            }
        });
        ivBtnPlusHS.setOnMouseClicked((me) -> {
            if (apHotSpots.isVisible()) {
                ivBtnPlusHS.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(0);
                apHotSpots.setMaxHeight(0);
                apHotSpots.setMinHeight(0);
                apHotSpots.setVisible(false);
            } else {
                ivBtnPlusHS.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apHotSpots.setPrefHeight(tailleHS);
                apHotSpots.setMaxHeight(tailleHS);
                apHotSpots.setMinHeight(tailleHS);
                apHotSpots.setVisible(true);
            }
        });

        /*        
         * ***************************************
         *     Panel Barre Navigation Classique 
         * ***************************************
         */
        AnchorPane apBarreClassique = new AnchorPane();
        apBarreClassique.setLayoutY(40);
        apBarreClassique.setPrefHeight(420);
        apBarreClassique.setMinWidth(vbOutils.getPrefWidth() - 20);

        cbBarreClassiqueVisible = new CheckBox(rbLocalisation.getString("interface.barreVisible"));
        cbBarreClassiqueVisible.setLayoutX(10);
        cbBarreClassiqueVisible.setLayoutY(5);
        cbBarreClassiqueVisible.setSelected(true);
        cbSuivantPrecedent = new CheckBox(rbLocalisation.getString("interface.SuivantPrecedent"));
        cbSuivantPrecedent.setLayoutX(10);
        cbSuivantPrecedent.setLayoutY(30);
        Label lblStyleBarreClassique = new Label(rbLocalisation.getString("interface.style"));
        lblStyleBarreClassique.setLayoutX(10);
        lblStyleBarreClassique.setLayoutY(60);

        cblisteStyleBarreClassique = new ComboBox();
        strListeStyles.stream().forEach((nomStyle) -> {
            cblisteStyleBarreClassique.getItems().add(nomStyle);
        });
        cblisteStyleBarreClassique.setLayoutX(150);
        cblisteStyleBarreClassique.setLayoutY(70);
        cblisteStyleBarreClassique.setValue(getStyleBarreClassique());
        cpCouleurBarreClassique = new ColorPicker(couleurBarreClassique);
        Label lblCouleurBarreClassique = new Label(rbLocalisation.getString("interface.couleurBarre"));
        lblCouleurBarreClassique.setLayoutX(20);
        lblCouleurBarreClassique.setLayoutY(110);
        cpCouleurBarreClassique.setLayoutX(150);
        cpCouleurBarreClassique.setLayoutY(105);

        rbTopLeftBarreClassique = new RadioButton();
        rbTopCenterBarreClassique = new RadioButton();
        rbTopRightBarreClassique = new RadioButton();
        rbMiddleLeftBarreClassique = new RadioButton();
        rbMiddleRightBarreClassique = new RadioButton();
        rbBottomLeftBarreClassique = new RadioButton();
        rbBottomCenterBarreClassique = new RadioButton();
        rbBottomRightBarreClassique = new RadioButton();

        rbTopLeftBarreClassique.setUserData("top:left");
        rbTopCenterBarreClassique.setUserData("top:center");
        rbTopRightBarreClassique.setUserData("top:right");
        rbMiddleLeftBarreClassique.setUserData("middle:left");
        rbMiddleRightBarreClassique.setUserData("middle:right");
        rbBottomLeftBarreClassique.setUserData("bottom:left");
        rbBottomCenterBarreClassique.setUserData("bottom:center");
        rbBottomRightBarreClassique.setUserData("bottom:right");

        rbTopLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbTopCenterBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbTopRightBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbMiddleLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbMiddleRightBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomLeftBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomCenterBarreClassique.setToggleGroup(tgPositionBarreClassique);
        rbBottomRightBarreClassique.setToggleGroup(tgPositionBarreClassique);

        int iPosX = 250;
        int iPosY = 140;

        rbTopLeftBarreClassique.setLayoutX(iPosX);
        rbTopCenterBarreClassique.setLayoutX(iPosX + 20);
        rbTopRightBarreClassique.setLayoutX(iPosX + 40);
        rbTopLeftBarreClassique.setLayoutY(iPosY);
        rbTopCenterBarreClassique.setLayoutY(iPosY);
        rbTopRightBarreClassique.setLayoutY(iPosY);

        rbMiddleLeftBarreClassique.setLayoutX(iPosX);
        rbMiddleRightBarreClassique.setLayoutX(iPosX + 40);
        rbMiddleLeftBarreClassique.setLayoutY(iPosY + 20);
        rbMiddleRightBarreClassique.setLayoutY(iPosY + 20);

        rbBottomLeftBarreClassique.setLayoutX(iPosX);
        rbBottomCenterBarreClassique.setLayoutX(iPosX + 20);
        rbBottomRightBarreClassique.setLayoutX(iPosX + 40);
        rbBottomLeftBarreClassique.setLayoutY(iPosY + 40);
        rbBottomCenterBarreClassique.setLayoutY(iPosY + 40);
        rbBottomRightBarreClassique.setLayoutY(iPosY + 40);
        rbBottomCenterBarreClassique.setSelected(true);
        Label lblPositionBarreClassique = new Label(rbLocalisation.getString("interface.position"));
        lblPositionBarreClassique.setLayoutX(10);
        lblPositionBarreClassique.setLayoutY(140);

        Label lblOffsetXBarreClassique = new Label("dX :");
        lblOffsetXBarreClassique.setLayoutX(25);
        lblOffsetXBarreClassique.setLayoutY(205);
        Label lblOffsetYBarreClassique = new Label("dY :");
        lblOffsetYBarreClassique.setLayoutX(175);
        lblOffsetYBarreClassique.setLayoutY(205);
        bdfOffsetXBarreClassique = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXBarreClassique.setLayoutX(50);
        bdfOffsetXBarreClassique.setLayoutY(200);
        bdfOffsetXBarreClassique.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarreClassique.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarreClassique.setMaxWidth(100);
        bdfOffsetYBarreClassique = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYBarreClassique.setLayoutX(200);
        bdfOffsetYBarreClassique.setLayoutY(200);
        bdfOffsetYBarreClassique.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBarreClassique.setMinValue(new BigDecimal(-2000));
        bdfOffsetYBarreClassique.setMaxWidth(100);

        Label lblEspacementBarreClassique = new Label(rbLocalisation.getString("interface.espacementBoutons"));
        lblEspacementBarreClassique.setLayoutX(10);
        lblEspacementBarreClassique.setLayoutY(240);
        slEspacementBarreClassique = new Slider(4, 25, 4);
        slEspacementBarreClassique.setLayoutX(170);
        slEspacementBarreClassique.setLayoutY(240);

        cbDeplacementsBarreClassique = new CheckBox(rbLocalisation.getString("interface.deplacementsVisible"));
        cbZoomBarreClassique = new CheckBox(rbLocalisation.getString("interface.zoomVisible"));
        cbOutilsBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsVisible"));
        cbSourisBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsSouris"));
        cbRotationBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsRotation"));
        cbFSBarreClassique = new CheckBox(rbLocalisation.getString("interface.outilsFS"));
        Label lblVisibiliteBarreClassique = new Label(rbLocalisation.getString("interface.visibilite"));
        lblVisibiliteBarreClassique.setLayoutX(10);
        lblVisibiliteBarreClassique.setLayoutY(270);

        cbDeplacementsBarreClassique.setLayoutX(100);
        cbDeplacementsBarreClassique.setLayoutY(290);
        cbDeplacementsBarreClassique.setSelected(true);
        cbZoomBarreClassique.setLayoutX(100);
        cbZoomBarreClassique.setLayoutY(310);
        cbZoomBarreClassique.setSelected(true);
        cbOutilsBarreClassique.setLayoutX(100);
        cbOutilsBarreClassique.setLayoutY(330);
        cbOutilsBarreClassique.setSelected(true);
        cbFSBarreClassique.setLayoutX(150);
        cbFSBarreClassique.setLayoutY(350);
        cbFSBarreClassique.setSelected(true);
        cbRotationBarreClassique.setLayoutX(150);
        cbRotationBarreClassique.setLayoutY(370);
        cbRotationBarreClassique.setSelected(true);
        cbSourisBarreClassique.setLayoutX(150);
        cbSourisBarreClassique.setLayoutY(390);
        cbSourisBarreClassique.setSelected(true);

        Label lblBarreClassique = new Label(rbLocalisation.getString("interface.barreBoutons"));
        lblBarreClassique.setPrefWidth(vbOutils.getPrefWidth());
        lblBarreClassique.setStyle("-fx-background-color : #666");
        lblBarreClassique.setTextFill(Color.WHITE);
        lblBarreClassique.setPadding(new Insets(5));
        lblBarreClassique.setLayoutX(10);
        lblBarreClassique.setLayoutY(10);
        ImageView ivBtnPlusBarreClassique = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBarreClassique.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBarreClassique.setLayoutY(11);
        apBarreClassique.getChildren().addAll(
                cbBarreClassiqueVisible, cbSuivantPrecedent, lblStyleBarreClassique, cblisteStyleBarreClassique,
                lblCouleurBarreClassique, cpCouleurBarreClassique,
                lblPositionBarreClassique,
                rbTopLeftBarreClassique, rbTopCenterBarreClassique, rbTopRightBarreClassique,
                rbMiddleLeftBarreClassique, rbMiddleRightBarreClassique,
                rbBottomLeftBarreClassique, rbBottomCenterBarreClassique, rbBottomRightBarreClassique,
                lblOffsetXBarreClassique, bdfOffsetXBarreClassique, lblOffsetYBarreClassique, bdfOffsetYBarreClassique,
                lblEspacementBarreClassique, slEspacementBarreClassique,
                lblVisibiliteBarreClassique, cbDeplacementsBarreClassique, cbZoomBarreClassique, cbOutilsBarreClassique,
                cbFSBarreClassique, cbRotationBarreClassique, cbSourisBarreClassique);

        double tailleInitiale = apBarreClassique.getPrefHeight();
        apBarreClassique.setPrefHeight(0);
        apBarreClassique.setMaxHeight(0);
        apBarreClassique.setMinHeight(0);
        apBarreClassique.setVisible(false);

        lblBarreClassique.setOnMouseClicked((me) -> {
            if (apBarreClassique.isVisible()) {
                apBarreClassique.setPrefHeight(0);
                apBarreClassique.setMaxHeight(0);
                apBarreClassique.setMinHeight(0);
                apBarreClassique.setVisible(false);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarreClassique.setPrefHeight(tailleInitiale);
                apBarreClassique.setMaxHeight(tailleInitiale);
                apBarreClassique.setMinHeight(tailleInitiale);
                apBarreClassique.setVisible(true);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusBarreClassique.setOnMouseClicked((me) -> {
            if (apBarreClassique.isVisible()) {
                apBarreClassique.setPrefHeight(0);
                apBarreClassique.setMaxHeight(0);
                apBarreClassique.setMinHeight(0);
                apBarreClassique.setVisible(false);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarreClassique.setPrefHeight(tailleInitiale);
                apBarreClassique.setMaxHeight(tailleInitiale);
                apBarreClassique.setMinHeight(tailleInitiale);
                apBarreClassique.setVisible(true);
                ivBtnPlusBarreClassique.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        /*        
         * ***************************************
         *     Panel Barre Navigation Personnalisee 
         * ***************************************
         */
        AnchorPane apBarrePersonnalisee = new AnchorPane();
        apBarrePersonnalisee.setLayoutY(40);
        apBarrePersonnalisee.setPrefHeight(680);
        apBarrePersonnalisee.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbBarrePersonnaliseeVisible = new CheckBox(rbLocalisation.getString("interface.barrePersonnaliseeVisible"));
        cbBarrePersonnaliseeVisible.setLayoutX(20);
        cbBarrePersonnaliseeVisible.setLayoutY(10);
        cbBarrePersonnaliseeVisible.setSelected(false);
        btnEditerBarre = new Button(rbLocalisation.getString("interface.barrePresonnaliseeEditer"));
        btnEditerBarre.setLayoutX(300);
        btnEditerBarre.setLayoutY(10);
        btnEditerBarre.setDisable(true);
        Label lblLienBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeLien"));
        lblLienBarrePersonnalisee.setLayoutX(20);
        lblLienBarrePersonnalisee.setLayoutY(45);
        tfLienImageBarrePersonnalisee = new TextField();
        tfLienImageBarrePersonnalisee.setLayoutX(100);
        tfLienImageBarrePersonnalisee.setLayoutY(40);
        tfLienImageBarrePersonnalisee.setPrefWidth(200);
        tfLienImageBarrePersonnalisee.setDisable(true);
        Button btnLienBarrePersonnalisee = new Button("...");
        btnLienBarrePersonnalisee.setLayoutX(310);
        btnLienBarrePersonnalisee.setLayoutY(40);

        ivBarrePersonnalisee = new ImageView();
        Pane paneImageBarrePersonnalisee = new Pane(ivBarrePersonnalisee);
        paneImageBarrePersonnalisee.setLayoutX(110);
        paneImageBarrePersonnalisee.setLayoutY(70);
        paneImageBarrePersonnalisee.setPrefHeight(150);
        paneImageBarrePersonnalisee.setPrefWidth(150);
        rbTopLeftBarrePersonnalisee = new RadioButton();
        rbTopCenterBarrePersonnalisee = new RadioButton();
        rbTopRightBarrePersonnalisee = new RadioButton();
        rbMiddleLeftBarrePersonnalisee = new RadioButton();
        rbMiddleRightBarrePersonnalisee = new RadioButton();
        rbBottomLeftBarrePersonnalisee = new RadioButton();
        rbBottomCenterBarrePersonnalisee = new RadioButton();
        rbBottomRightBarrePersonnalisee = new RadioButton();

        rbTopLeftBarrePersonnalisee.setUserData("top:left");
        rbTopCenterBarrePersonnalisee.setUserData("top:center");
        rbTopRightBarrePersonnalisee.setUserData("top:right");
        rbMiddleLeftBarrePersonnalisee.setUserData("middle:left");
        rbMiddleRightBarrePersonnalisee.setUserData("middle:right");
        rbBottomLeftBarrePersonnalisee.setUserData("bottom:left");
        rbBottomCenterBarrePersonnalisee.setUserData("bottom:center");
        rbBottomRightBarrePersonnalisee.setUserData("bottom:right");

        rbTopLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbTopCenterBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbTopRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbMiddleLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbMiddleRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomLeftBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomCenterBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);
        rbBottomRightBarrePersonnalisee.setToggleGroup(grpPositionBarrePersonnalisee);

        int iPos1X = 250;
        int iPos1Y = 240;

        rbTopLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbTopCenterBarrePersonnalisee.setLayoutX(iPos1X + 20);
        rbTopRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbTopLeftBarrePersonnalisee.setLayoutY(iPos1Y);
        rbTopCenterBarrePersonnalisee.setLayoutY(iPos1Y);
        rbTopRightBarrePersonnalisee.setLayoutY(iPos1Y);

        rbMiddleLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbMiddleRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbMiddleLeftBarrePersonnalisee.setLayoutY(iPos1Y + 20);
        rbMiddleRightBarrePersonnalisee.setLayoutY(iPos1Y + 20);

        rbBottomLeftBarrePersonnalisee.setLayoutX(iPos1X);
        rbBottomCenterBarrePersonnalisee.setLayoutX(iPos1X + 20);
        rbBottomRightBarrePersonnalisee.setLayoutX(iPos1X + 40);
        rbBottomLeftBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomCenterBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomRightBarrePersonnalisee.setLayoutY(iPos1Y + 40);
        rbBottomRightBarrePersonnalisee.setSelected(true);
        Label lblPositionBarrePersonnalisee = new Label(rbLocalisation.getString("interface.position"));
        lblPositionBarrePersonnalisee.setLayoutX(20);
        lblPositionBarrePersonnalisee.setLayoutY(250);

        Label lblOffsetXBarrePersonnalisee = new Label("dX :");
        lblOffsetXBarrePersonnalisee.setLayoutX(25);
        lblOffsetXBarrePersonnalisee.setLayoutY(315);
        Label lblOffsetYBarrePersonnalisee = new Label("dY :");
        lblOffsetYBarrePersonnalisee.setLayoutX(175);
        lblOffsetYBarrePersonnalisee.setLayoutY(315);
        bdfOffsetXBarrePersonnalisee = new BigDecimalField(new BigDecimal(getOffsetXBarrePersonnalisee()));
        bdfOffsetXBarrePersonnalisee.setLayoutX(50);
        bdfOffsetXBarrePersonnalisee.setLayoutY(310);
        bdfOffsetXBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetXBarrePersonnalisee.setMaxWidth(100);
        bdfOffsetYBarrePersonnalisee = new BigDecimalField(new BigDecimal(getOffsetYBarrePersonnalisee()));
        bdfOffsetYBarrePersonnalisee.setLayoutX(200);
        bdfOffsetYBarrePersonnalisee.setLayoutY(310);
        bdfOffsetYBarrePersonnalisee.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBarrePersonnalisee.setMinValue(new BigDecimal(-2000));
        bdfOffsetYBarrePersonnalisee.setMaxWidth(100);
        Label lblTailleBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTaille"));
        lblTailleBarrePersonnalisee.setLayoutX(20);
        lblTailleBarrePersonnalisee.setLayoutY(350);
        sltailleBarrePersonnalisee = new Slider(10, 200, getTailleBarrePersonnalisee());
        sltailleBarrePersonnalisee.setLayoutX(150);
        sltailleBarrePersonnalisee.setLayoutY(350);

        Label lblTailleBarrePersonnaliseeIcones = new Label(rbLocalisation.getString("interface.barrePersonnaliseeTailleIcones"));
        lblTailleBarrePersonnaliseeIcones.setLayoutX(20);
        lblTailleBarrePersonnaliseeIcones.setLayoutY(380);
        sltailleIconesBarrePersonnalisee = new Slider(10, 60, getTailleIconesBarrePersonnalisee());
        sltailleIconesBarrePersonnalisee.setLayoutX(150);
        sltailleIconesBarrePersonnalisee.setLayoutY(380);

        rbCouleurOrigineBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurOrigine"));
        rbCouleurOrigineBarrePersonnalisee.setLayoutX(20);
        rbCouleurOrigineBarrePersonnalisee.setLayoutY(410);
        rbCouleurOrigineBarrePersonnalisee.setSelected(true);
        rbCouleurOrigineBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurOrigineBarrePersonnalisee.setUserData(true);

        rbCouleurPersBarrePersonnalisee = new RadioButton(rbLocalisation.getString("interface.barrePersonnaliseeCouleurPersonnalisee"));
        rbCouleurPersBarrePersonnalisee.setLayoutX(20);
        rbCouleurPersBarrePersonnalisee.setLayoutY(440);
        rbCouleurPersBarrePersonnalisee.setToggleGroup(grpCouleurBarrePersonnalisee);
        rbCouleurPersBarrePersonnalisee.setUserData(false);

        cpCouleurBarrePersonnalisee = new ColorPicker(couleurBarrePersonnalisee);
        cpCouleurBarrePersonnalisee.setLayoutX(180);
        cpCouleurBarrePersonnalisee.setLayoutY(435);
        cpCouleurBarrePersonnalisee.setDisable(true);
        cbDeplacementsBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.deplacementsVisible"));
        cbZoomBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.zoomVisible"));
        cbSourisBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsSouris"));
        cbRotationBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsRotation"));
        cbFSBarrePersonnalisee = new CheckBox(rbLocalisation.getString("interface.outilsFS"));
        Label lblVisibiliteBarrePersonnalisee = new Label(rbLocalisation.getString("interface.visibilite"));
        lblVisibiliteBarrePersonnalisee.setLayoutX(20);
        lblVisibiliteBarrePersonnalisee.setLayoutY(480);

        cbDeplacementsBarrePersonnalisee.setLayoutX(100);
        cbDeplacementsBarrePersonnalisee.setLayoutY(500);
        cbDeplacementsBarrePersonnalisee.setSelected(true);
        cbZoomBarrePersonnalisee.setLayoutX(100);
        cbZoomBarrePersonnalisee.setLayoutY(520);
        cbZoomBarrePersonnalisee.setSelected(true);
        cbFSBarrePersonnalisee.setLayoutX(150);
        cbFSBarrePersonnalisee.setLayoutY(560);
        cbFSBarrePersonnalisee.setSelected(true);
        cbRotationBarrePersonnalisee.setLayoutX(150);
        cbRotationBarrePersonnalisee.setLayoutY(580);
        cbRotationBarrePersonnalisee.setSelected(true);
        cbSourisBarrePersonnalisee.setLayoutX(150);
        cbSourisBarrePersonnalisee.setLayoutY(600);
        cbSourisBarrePersonnalisee.setSelected(true);
        Label lblLien1BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "1");
        lblLien1BarrePersonnalisee.setLayoutX(20);
        lblLien1BarrePersonnalisee.setLayoutY(630);
        tfLien1BarrePersonnalisee = new TextField("");
        tfLien1BarrePersonnalisee.setPrefWidth(200);
        tfLien1BarrePersonnalisee.setLayoutX(100);
        tfLien1BarrePersonnalisee.setLayoutY(630);
        tfLien1BarrePersonnalisee.setDisable(true);
        Label lblLien2BarrePersonnalisee = new Label(rbLocalisation.getString("interface.lienBarrePersonalisee") + "2");
        lblLien2BarrePersonnalisee.setLayoutX(20);
        lblLien2BarrePersonnalisee.setLayoutY(660);
        tfLien2BarrePersonnalisee = new TextField("");
        tfLien2BarrePersonnalisee.setPrefWidth(200);
        tfLien2BarrePersonnalisee.setLayoutX(100);
        tfLien2BarrePersonnalisee.setLayoutY(660);
        tfLien2BarrePersonnalisee.setDisable(true);
        Label lblBarrePersonnalisee = new Label(rbLocalisation.getString("interface.barreBoutonsPersonalisee"));
        lblBarrePersonnalisee.setPrefWidth(vbOutils.getPrefWidth());
        lblBarrePersonnalisee.setStyle("-fx-background-color : #666");
        lblBarrePersonnalisee.setTextFill(Color.WHITE);
        lblBarrePersonnalisee.setPadding(new Insets(5));
        lblBarrePersonnalisee.setLayoutX(10);
        lblBarrePersonnalisee.setLayoutY(10);
        ImageView ivBtnPlusBarrePersonnalisee = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBarrePersonnalisee.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBarrePersonnalisee.setLayoutY(11);
        apBarrePersonnalisee.getChildren().addAll(
                cbBarrePersonnaliseeVisible, btnEditerBarre,
                lblLienBarrePersonnalisee, tfLienImageBarrePersonnalisee, btnLienBarrePersonnalisee,
                paneImageBarrePersonnalisee,
                lblPositionBarrePersonnalisee,
                rbTopLeftBarrePersonnalisee, rbTopCenterBarrePersonnalisee, rbTopRightBarrePersonnalisee,
                rbMiddleLeftBarrePersonnalisee, rbMiddleRightBarrePersonnalisee,
                rbBottomLeftBarrePersonnalisee, rbBottomCenterBarrePersonnalisee, rbBottomRightBarrePersonnalisee,
                lblOffsetXBarrePersonnalisee, bdfOffsetXBarrePersonnalisee, lblOffsetYBarrePersonnalisee, bdfOffsetYBarrePersonnalisee,
                lblTailleBarrePersonnalisee, sltailleBarrePersonnalisee,
                lblTailleBarrePersonnaliseeIcones, sltailleIconesBarrePersonnalisee,
                rbCouleurOrigineBarrePersonnalisee,
                rbCouleurPersBarrePersonnalisee, cpCouleurBarrePersonnalisee,
                lblVisibiliteBarrePersonnalisee, cbDeplacementsBarrePersonnalisee, cbZoomBarrePersonnalisee,
                cbFSBarrePersonnalisee, cbRotationBarrePersonnalisee, cbSourisBarrePersonnalisee,
                lblLien1BarrePersonnalisee, tfLien1BarrePersonnalisee,
                lblLien2BarrePersonnalisee, tfLien2BarrePersonnalisee
        );

        double tailleInitiale1 = apBarrePersonnalisee.getPrefHeight();
        apBarrePersonnalisee.setPrefHeight(0);
        apBarrePersonnalisee.setMaxHeight(0);
        apBarrePersonnalisee.setMinHeight(0);
        apBarrePersonnalisee.setVisible(false);

        lblBarrePersonnalisee.setOnMouseClicked((me) -> {
            if (apBarrePersonnalisee.isVisible()) {
                apBarrePersonnalisee.setPrefHeight(0);
                apBarrePersonnalisee.setMaxHeight(0);
                apBarrePersonnalisee.setMinHeight(0);
                apBarrePersonnalisee.setVisible(false);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarrePersonnalisee.setPrefHeight(tailleInitiale1);
                apBarrePersonnalisee.setMaxHeight(tailleInitiale1);
                apBarrePersonnalisee.setMinHeight(tailleInitiale1);
                apBarrePersonnalisee.setVisible(true);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });
        ivBtnPlusBarrePersonnalisee.setOnMouseClicked((me) -> {
            if (apBarrePersonnalisee.isVisible()) {
                apBarrePersonnalisee.setPrefHeight(0);
                apBarrePersonnalisee.setMaxHeight(0);
                apBarrePersonnalisee.setMinHeight(0);
                apBarrePersonnalisee.setVisible(false);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
            } else {
                apBarrePersonnalisee.setPrefHeight(tailleInitiale1);
                apBarrePersonnalisee.setMaxHeight(tailleInitiale1);
                apBarrePersonnalisee.setMinHeight(tailleInitiale1);
                apBarrePersonnalisee.setVisible(true);
                ivBtnPlusBarrePersonnalisee.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
            }
        });

        /*
         * ********************************************
         *      Panel Boussole 
         * ********************************************
         */
        AnchorPane apBoussole = new AnchorPane();

        apBoussole.setLayoutY(40);
        apBoussole.setPrefHeight(340);
        apBoussole.setMinWidth(vbOutils.getPrefWidth() - 20);
        double tailleBouss = apBoussole.getPrefHeight();
        cbAfficheBoussole = new CheckBox(rbLocalisation.getString("interface.affichageBoussole"));
        cbAfficheBoussole.setLayoutX(10);
        cbAfficheBoussole.setLayoutY(10);
        apBoussole.getChildren().add(cbAfficheBoussole);
        Label lblPanelBoussole = new Label(rbLocalisation.getString("interface.boussole"));
        lblPanelBoussole.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelBoussole.setStyle("-fx-background-color : #666");
        lblPanelBoussole.setTextFill(Color.WHITE);
        lblPanelBoussole.setPadding(new Insets(5));
        lblPanelBoussole.setLayoutX(10);
        lblPanelBoussole.setLayoutY(10);
        ImageView ivBtnPlusBouss = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusBouss.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusBouss.setLayoutY(11);
        Label lblChoixBoussole = new Label(rbLocalisation.getString("interface.choixImgBoussole"));
        lblChoixBoussole.setLayoutX(10);
        lblChoixBoussole.setLayoutY(40);
        apBoussole.getChildren().add(lblChoixBoussole);

        int iNombreBoussoles = strListeBoussoles.size();
        ImageView[] ivBoussoles = new ImageView[iNombreBoussoles];
        i = 0;
        for (String strNomImage : strListeBoussoles) {
            Pane paneFond = new Pane();
            ivBoussoles[i] = new ImageView(new Image("file:" + strRepertBoussoles + File.separator + strNomImage, -1, 60, true, true, true));
            int iCol = i % 3;
            int iRow = i / 3;
            xHS = iCol * 70 + 95;
            yHS = iRow * 70 + 60;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setPrefHeight(60);
            paneFond.setPrefWidth(60);
            paneFond.setOpacity(1);
            paneFond.setUserData(strNomImage);
            paneFond.setStyle("-fx-background-color : rgba(255,255,255,0)");
            paneFond.setOnMouseClicked((me) -> {
                setStrImageBoussole((String) ((Pane) me.getSource()).getUserData());
                afficheBoussole();
            });
            paneFond.getChildren().add(ivBoussoles[i]);

            apBoussole.getChildren().add(paneFond);
            i++;

        }
        Label lblPositBoussole = new Label(rbLocalisation.getString("interface.choixPositBoussole"));
        lblPositBoussole.setLayoutX(10);
        lblPositBoussole.setLayoutY(160);
        apBoussole.getChildren().add(lblPositBoussole);

        rbBoussTopLeft = new RadioButton();
        rbBoussTopRight = new RadioButton();
        rbBoussBottomLeft = new RadioButton();
        rbBoussBottomRight = new RadioButton();

        rbBoussTopLeft.setUserData("top:left");
        rbBoussTopRight.setUserData("top:right");
        rbBoussBottomLeft.setUserData("bottom:left");
        rbBoussBottomRight.setUserData("bottom:right");

        rbBoussTopLeft.setToggleGroup(tgPosBouss);
        rbBoussTopRight.setToggleGroup(tgPosBouss);
        rbBoussBottomLeft.setToggleGroup(tgPosBouss);
        rbBoussBottomRight.setToggleGroup(tgPosBouss);

        iPosX = 200;
        iPosY = 160;

        rbBoussTopLeft.setLayoutX(iPosX);
        rbBoussTopRight.setLayoutX(iPosX + 20);
        rbBoussTopLeft.setLayoutY(iPosY);
        rbBoussTopRight.setLayoutY(iPosY);

        rbBoussBottomLeft.setLayoutX(iPosX);
        rbBoussBottomRight.setLayoutX(iPosX + 20);
        rbBoussBottomLeft.setLayoutY(iPosY + 20);
        rbBoussBottomRight.setLayoutY(iPosY + 20);
        apBoussole.getChildren().addAll(
                rbBoussTopLeft, rbBoussTopRight,
                rbBoussBottomLeft, rbBoussBottomRight
        );
        Label lblBoussDXSpinner = new Label("dX :");
        lblBoussDXSpinner.setLayoutX(25);
        lblBoussDXSpinner.setLayoutY(210);
        Label lblBoussDYSpinner = new Label("dY :");
        lblBoussDYSpinner.setLayoutX(175);
        lblBoussDYSpinner.setLayoutY(210);
        bdfOffsetXBoussole = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXBoussole.setLayoutX(50);
        bdfOffsetXBoussole.setLayoutY(205);
        bdfOffsetXBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetXBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetXBoussole.setNumber(new BigDecimal(20));
        bdfOffsetXBoussole.setMaxWidth(100);
        bdfOffsetYBoussole = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYBoussole.setLayoutX(200);
        bdfOffsetYBoussole.setLayoutY(205);
        bdfOffsetYBoussole.setMaxValue(new BigDecimal(2000));
        bdfOffsetYBoussole.setMinValue(new BigDecimal(0));
        bdfOffsetYBoussole.setNumber(new BigDecimal(20));
        bdfOffsetYBoussole.setMaxWidth(100);
        apBoussole.getChildren().addAll(lblBoussDXSpinner, bdfOffsetXBoussole,
                lblBoussDYSpinner, bdfOffsetYBoussole
        );
        Label lblTailleBouss = new Label(rbLocalisation.getString("interface.tailleBoussole"));
        lblTailleBouss.setLayoutX(10);
        lblTailleBouss.setLayoutY(245);
        slTailleBoussole = new Slider(50, 200, 100);
        slTailleBoussole.setLayoutX(200);
        slTailleBoussole.setLayoutY(245);
        Label lblOpaciteBouss = new Label(rbLocalisation.getString("interface.opaciteBoussole"));
        lblOpaciteBouss.setLayoutX(10);
        lblOpaciteBouss.setLayoutY(275);
        slOpaciteBoussole = new Slider(0, 1.0, 0.8);
        slOpaciteBoussole.setLayoutX(200);
        slOpaciteBoussole.setLayoutY(275);
        cbAiguilleMobile = new CheckBox(rbLocalisation.getString("interface.aiguilleMobile"));
        cbAiguilleMobile.setLayoutX(10);
        cbAiguilleMobile.setLayoutY(305);
        cbAiguilleMobile.setSelected(true);

        apBoussole.getChildren().addAll(
                lblTailleBouss, slTailleBoussole,
                lblOpaciteBouss, slOpaciteBoussole,
                cbAiguilleMobile
        );
        apBoussole.setPrefHeight(0);
        apBoussole.setMaxHeight(0);
        apBoussole.setMinHeight(0);
        apBoussole.setVisible(false);

        lblPanelBoussole.setOnMouseClicked((me) -> {
            if (apBoussole.isVisible()) {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apBoussole.setPrefHeight(0);
                apBoussole.setMaxHeight(0);
                apBoussole.setMinHeight(0);
                apBoussole.setVisible(false);
            } else {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apBoussole.setPrefHeight(tailleBouss);
                apBoussole.setMaxHeight(tailleBouss);
                apBoussole.setMinHeight(tailleBouss);
                apBoussole.setVisible(true);
            }
        });
        ivBtnPlusBouss.setOnMouseClicked((me) -> {
            if (apBoussole.isVisible()) {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apBoussole.setPrefHeight(0);
                apBoussole.setMaxHeight(0);
                apBoussole.setMinHeight(0);
                apBoussole.setVisible(false);
            } else {
                ivBtnPlusBouss.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apBoussole.setPrefHeight(tailleBouss);
                apBoussole.setMaxHeight(tailleBouss);
                apBoussole.setMinHeight(tailleBouss);
                apBoussole.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Masque 
         * ********************************************
         */
        AnchorPane apMasque = new AnchorPane();

        apMasque.setLayoutY(40);
        apMasque.setPrefHeight(590);
        apMasque.setMinWidth(vbOutils.getPrefWidth() - 20);
        double taillePanelMasque = apMasque.getPrefHeight();
        cbAfficheMasque = new CheckBox(rbLocalisation.getString("interface.affichageMasque"));
        cbAfficheMasque.setLayoutX(10);
        cbAfficheMasque.setLayoutY(10);
        apMasque.getChildren().add(cbAfficheMasque);
        Label lblPanelMasque = new Label(rbLocalisation.getString("interface.masque"));
        lblPanelMasque.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelMasque.setStyle("-fx-background-color : #666");
        lblPanelMasque.setTextFill(Color.WHITE);
        lblPanelMasque.setPadding(new Insets(5));
        lblPanelMasque.setLayoutX(10);
        lblPanelMasque.setLayoutY(10);
        ImageView ivBtnPlusMasque = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusMasque.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusMasque.setLayoutY(11);
        Label lblChoixMasque = new Label(rbLocalisation.getString("interface.choixImgMasque"));
        lblChoixMasque.setLayoutX(10);
        lblChoixMasque.setLayoutY(40);
        apMasque.getChildren().add(lblChoixMasque);

        int iNombreMasques = strListeMasques.size();
        ImageView[] ivMasques = new ImageView[iNombreMasques];
        i = 0;
        for (String strNomImage : strListeMasques) {
            Pane paneFond = new Pane();
            ivMasques[i] = new ImageView(new Image("file:" + strRepertMasques + File.separator + strNomImage, -1, 30, true, true, true));
            int iCol = i % 4;
            int iRow = i / 4;
            xHS = iCol * 35 + 15;
            yHS = iRow * 35 + 60;
            paneFond.setLayoutX(xHS);
            paneFond.setLayoutY(yHS);
            paneFond.setPrefHeight(30);
            paneFond.setPrefWidth(30);
            paneFond.setOpacity(1);
            paneFond.setUserData(strNomImage);
            paneFond.setStyle("-fx-background-color : rgba(255,255,255,0)");
            paneFond.setOnMouseClicked((me) -> {
                setStrImageMasque((String) ((Pane) me.getSource()).getUserData());
                apVisualisation.getChildren().remove(ivMasque);
                chargeBarre(getStyleBarreClassique(), getStrStyleHotSpots(), getStrImageMasque());
                afficheMasque();
            });
            paneFond.getChildren().add(ivMasques[i]);
            apMasque.getChildren().add(paneFond);
            i++;

        }
        cpCouleurMasques = new ColorPicker(couleurMasque);
        Label lblCouleurMasque = new Label(rbLocalisation.getString("interface.couleurMasque"));
        lblCouleurMasque.setLayoutX(170);
        lblCouleurMasque.setLayoutY(40);
        cpCouleurMasques.setLayoutX(180);
        cpCouleurMasques.setLayoutY(60);
        apMasque.getChildren().addAll(lblCouleurMasque, cpCouleurMasques);

        Label lblPositMasque = new Label(rbLocalisation.getString("interface.choixPositMasque"));
        lblPositMasque.setLayoutX(10);
        int iBasImages = ((i - 1) / 4 + 1) * 35;
        lblPositMasque.setLayoutY(70 + iBasImages);
        apMasque.getChildren().add(lblPositMasque);

        rbMasqueTopLeft = new RadioButton();
        rbMasqueTopRight = new RadioButton();
        rbMasqueBottomLeft = new RadioButton();
        rbMasqueBottomRight = new RadioButton();

        rbMasqueTopLeft.setUserData("top:left");
        rbMasqueTopRight.setUserData("top:right");
        rbMasqueBottomLeft.setUserData("bottom:left");
        rbMasqueBottomRight.setUserData("bottom:right");

        rbMasqueTopLeft.setToggleGroup(tgPosMasque);
        rbMasqueTopRight.setToggleGroup(tgPosMasque);
        rbMasqueBottomLeft.setToggleGroup(tgPosMasque);
        rbMasqueBottomRight.setToggleGroup(tgPosMasque);

        iPosX = 200;
        iPosY = 70 + iBasImages;

        rbMasqueTopLeft.setLayoutX(iPosX);
        rbMasqueTopRight.setLayoutX(iPosX + 20);
        rbMasqueTopLeft.setLayoutY(iPosY);
        rbMasqueTopRight.setLayoutY(iPosY);

        rbMasqueBottomLeft.setLayoutX(iPosX);
        rbMasqueBottomRight.setLayoutX(iPosX + 20);
        rbMasqueBottomLeft.setLayoutY(iPosY + 20);
        rbMasqueBottomRight.setLayoutY(iPosY + 20);
        apMasque.getChildren().addAll(
                rbMasqueTopLeft, rbMasqueTopRight,
                rbMasqueBottomLeft, rbMasqueBottomRight
        );
        Label lblMasqueDXSpinner = new Label("dX :");
        lblMasqueDXSpinner.setLayoutX(25);
        lblMasqueDXSpinner.setLayoutY(128 + iBasImages);
        Label lblMasqueDYSpinner = new Label("dY :");
        lblMasqueDYSpinner.setLayoutX(175);
        lblMasqueDYSpinner.setLayoutY(128 + iBasImages);
        bdfOffsetXMasque = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXMasque.setLayoutX(50);
        bdfOffsetXMasque.setLayoutY(123 + iBasImages);
        bdfOffsetXMasque.setMaxValue(new BigDecimal(2000));
        bdfOffsetXMasque.setMinValue(new BigDecimal(0));
        bdfOffsetXMasque.setNumber(new BigDecimal(20));
        bdfOffsetXMasque.setMaxWidth(100);
        bdfOffsetYMasque = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYMasque.setLayoutX(200);
        bdfOffsetYMasque.setLayoutY(123 + iBasImages);
        bdfOffsetYMasque.setMaxValue(new BigDecimal(2000));
        bdfOffsetYMasque.setMinValue(new BigDecimal(0));
        bdfOffsetYMasque.setNumber(new BigDecimal(20));
        bdfOffsetYMasque.setMaxWidth(100);
        apMasque.getChildren().addAll(
                lblMasqueDXSpinner, bdfOffsetXMasque,
                lblMasqueDYSpinner, bdfOffsetYMasque
        );
        Label lblTailleMasque = new Label(rbLocalisation.getString("interface.tailleMasque"));
        lblTailleMasque.setLayoutX(10);
        lblTailleMasque.setLayoutY(160 + iBasImages);
        slTailleMasque = new Slider(15, 60, 30);
        slTailleMasque.setLayoutX(200);
        slTailleMasque.setLayoutY(160 + iBasImages);
        Label lblOpaciteMasque = new Label(rbLocalisation.getString("interface.opaciteMasque"));
        lblOpaciteMasque.setLayoutX(10);
        lblOpaciteMasque.setLayoutY(190 + iBasImages);
        slOpaciteMasque = new Slider(0, 1.0, 0.8);
        slOpaciteMasque.setLayoutX(200);
        slOpaciteMasque.setLayoutY(190 + iBasImages);
        cbMasqueNavigation = new CheckBox(rbLocalisation.getString("interface.masqueNavigation"));
        cbMasqueNavigation.setLayoutX(60);
        cbMasqueNavigation.setLayoutY(220 + iBasImages);
        cbMasqueNavigation.setSelected(true);
        cbMasqueBoussole = new CheckBox(rbLocalisation.getString("interface.masqueBoussole"));
        cbMasqueBoussole.setLayoutX(60);
        cbMasqueBoussole.setLayoutY(250 + iBasImages);
        cbMasqueBoussole.setSelected(true);
        cbMasqueTitre = new CheckBox(rbLocalisation.getString("interface.masqueTitre"));
        cbMasqueTitre.setLayoutX(60);
        cbMasqueTitre.setLayoutY(280 + iBasImages);
        cbMasqueTitre.setSelected(true);
        cbMasquePlan = new CheckBox(rbLocalisation.getString("interface.masquePlan"));
        cbMasquePlan.setLayoutX(60);
        cbMasquePlan.setLayoutY(310 + iBasImages);
        cbMasquePlan.setSelected(true);
        cbMasqueReseaux = new CheckBox(rbLocalisation.getString("interface.masqueReseaux"));
        cbMasqueReseaux.setLayoutX(60);
        cbMasqueReseaux.setLayoutY(340 + iBasImages);
        cbMasqueReseaux.setSelected(true);
        cbMasqueReseaux.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                setbMasqueReseaux((boolean) new_val);
            }
        });
        cbMasqueVignettes = new CheckBox(rbLocalisation.getString("interface.masqueVignettes"));
        cbMasqueVignettes.setLayoutX(60);
        cbMasqueVignettes.setLayoutY(370 + iBasImages);
        cbMasqueVignettes.setSelected(true);
        cbMasqueCombo = new CheckBox(rbLocalisation.getString("interface.masqueCombo"));
        cbMasqueCombo.setLayoutX(60);
        cbMasqueCombo.setLayoutY(400 + iBasImages);
        cbMasqueCombo.setSelected(true);

        cbMasqueSuivPrec = new CheckBox(rbLocalisation.getString("interface.masqueSuivPrec"));
        cbMasqueSuivPrec.setLayoutX(60);
        cbMasqueSuivPrec.setLayoutY(430 + iBasImages);
        cbMasqueSuivPrec.setSelected(true);
        cbMasqueHotspots = new CheckBox(rbLocalisation.getString("interface.masqueHotspots"));
        cbMasqueHotspots.setLayoutX(60);
        cbMasqueHotspots.setLayoutY(460 + iBasImages);
        cbMasqueHotspots.setSelected(true);

        apMasque.getChildren().addAll(
                lblTailleMasque, slTailleMasque,
                lblOpaciteMasque, slOpaciteMasque,
                cbMasqueNavigation,
                cbMasqueBoussole,
                cbMasqueTitre,
                cbMasquePlan,
                cbMasqueReseaux,
                cbMasqueVignettes,
                cbMasqueCombo,
                cbMasqueSuivPrec,
                cbMasqueHotspots
        );
        apMasque.setPrefHeight(0);
        apMasque.setMaxHeight(0);
        apMasque.setMinHeight(0);
        apMasque.setVisible(false);

        lblPanelMasque.setOnMouseClicked((me) -> {
            if (apMasque.isVisible()) {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMasque.setPrefHeight(0);
                apMasque.setMaxHeight(0);
                apMasque.setMinHeight(0);
                apMasque.setVisible(false);
            } else {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMasque.setPrefHeight(taillePanelMasque);
                apMasque.setMaxHeight(taillePanelMasque);
                apMasque.setMinHeight(taillePanelMasque);
                apMasque.setVisible(true);
            }
        });
        ivBtnPlusMasque.setOnMouseClicked((me) -> {
            if (apMasque.isVisible()) {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMasque.setPrefHeight(0);
                apMasque.setMaxHeight(0);
                apMasque.setMinHeight(0);
                apMasque.setVisible(false);
            } else {
                ivBtnPlusMasque.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMasque.setPrefHeight(taillePanelMasque);
                apMasque.setMaxHeight(taillePanelMasque);
                apMasque.setMinHeight(taillePanelMasque);
                apMasque.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel ReseauxSociaux 
         * ********************************************
         */
        AnchorPane apReseauxSociaux = new AnchorPane();

        apReseauxSociaux.setLayoutY(40);
        apReseauxSociaux.setPrefHeight(310);
        apReseauxSociaux.setMinWidth(vbOutils.getPrefWidth() - 20);
        double taillePanelReseauxSociaux = apReseauxSociaux.getPrefHeight();
        cbAfficheReseauxSociaux = new CheckBox(rbLocalisation.getString("interface.affichageReseauxSociaux"));
        cbAfficheReseauxSociaux.setLayoutX(10);
        cbAfficheReseauxSociaux.setLayoutY(10);
        apReseauxSociaux.getChildren().add(cbAfficheReseauxSociaux);
        Label lblPanelReseauxSociaux = new Label(rbLocalisation.getString("interface.reseauxSociaux"));
        lblPanelReseauxSociaux.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelReseauxSociaux.setStyle("-fx-background-color : #666");
        lblPanelReseauxSociaux.setTextFill(Color.WHITE);
        lblPanelReseauxSociaux.setPadding(new Insets(5));
        lblPanelReseauxSociaux.setLayoutX(10);
        lblPanelReseauxSociaux.setLayoutY(10);
        ImageView ivBtnPlusReseauxSociaux = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusReseauxSociaux.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusReseauxSociaux.setLayoutY(11);
        Label lblPositReseauxSociaux = new Label(rbLocalisation.getString("interface.choixPositReseauxSociaux"));
        lblPositReseauxSociaux.setLayoutX(10);
        iBasImages = -30;
        lblPositReseauxSociaux.setLayoutY(70 + iBasImages);
        apReseauxSociaux.getChildren().add(lblPositReseauxSociaux);

        rbReseauxSociauxTopLeft = new RadioButton();
        rbReseauxSociauxTopRight = new RadioButton();
        rbReseauxSociauxBottomLeft = new RadioButton();
        rbReseauxSociauxBottomRight = new RadioButton();

        rbReseauxSociauxTopLeft.setUserData("top:left");
        rbReseauxSociauxTopRight.setUserData("top:right");
        rbReseauxSociauxBottomLeft.setUserData("bottom:left");
        rbReseauxSociauxBottomRight.setUserData("bottom:right");

        rbReseauxSociauxTopLeft.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxTopRight.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxBottomLeft.setToggleGroup(tgPosReseauxSociaux);
        rbReseauxSociauxBottomRight.setToggleGroup(tgPosReseauxSociaux);

        iPosX = 200;
        iPosY = 70 + iBasImages;

        rbReseauxSociauxTopLeft.setLayoutX(iPosX);
        rbReseauxSociauxTopRight.setLayoutX(iPosX + 20);
        rbReseauxSociauxTopLeft.setLayoutY(iPosY);
        rbReseauxSociauxTopRight.setLayoutY(iPosY);

        rbReseauxSociauxBottomLeft.setLayoutX(iPosX);
        rbReseauxSociauxBottomRight.setLayoutX(iPosX + 20);
        rbReseauxSociauxBottomLeft.setLayoutY(iPosY + 20);
        rbReseauxSociauxBottomRight.setLayoutY(iPosY + 20);
        apReseauxSociaux.getChildren().addAll(
                rbReseauxSociauxTopLeft, rbReseauxSociauxTopRight,
                rbReseauxSociauxBottomLeft, rbReseauxSociauxBottomRight
        );
        Label lblReseauxSociauxDXSpinner = new Label("dX :");
        lblReseauxSociauxDXSpinner.setLayoutX(25);
        lblReseauxSociauxDXSpinner.setLayoutY(128 + iBasImages);
        Label lblReseauxSociauxDYSpinner = new Label("dY :");
        lblReseauxSociauxDYSpinner.setLayoutX(175);
        lblReseauxSociauxDYSpinner.setLayoutY(128 + iBasImages);
        bdfOffsetXReseauxSociaux = new BigDecimalField(new BigDecimal(getOffsetXBarreClassique()));
        bdfOffsetXReseauxSociaux.setLayoutX(50);
        bdfOffsetXReseauxSociaux.setLayoutY(123 + iBasImages);
        bdfOffsetXReseauxSociaux.setMaxValue(new BigDecimal(2000));
        bdfOffsetXReseauxSociaux.setMinValue(new BigDecimal(0));
        bdfOffsetXReseauxSociaux.setNumber(new BigDecimal(20));
        bdfOffsetXReseauxSociaux.setMaxWidth(100);
        bdfOffsetYreseauxSociaux = new BigDecimalField(new BigDecimal(getOffsetYBarreClassique()));
        bdfOffsetYreseauxSociaux.setLayoutX(200);
        bdfOffsetYreseauxSociaux.setLayoutY(123 + iBasImages);
        bdfOffsetYreseauxSociaux.setMaxValue(new BigDecimal(2000));
        bdfOffsetYreseauxSociaux.setMinValue(new BigDecimal(0));
        bdfOffsetYreseauxSociaux.setNumber(new BigDecimal(20));
        bdfOffsetYreseauxSociaux.setMaxWidth(100);
        apReseauxSociaux.getChildren().addAll(
                lblReseauxSociauxDXSpinner, bdfOffsetXReseauxSociaux,
                lblReseauxSociauxDYSpinner, bdfOffsetYreseauxSociaux
        );
        Label lblTailleReseauxSociaux = new Label(rbLocalisation.getString("interface.tailleReseauxSociaux"));
        lblTailleReseauxSociaux.setLayoutX(10);
        lblTailleReseauxSociaux.setLayoutY(160 + iBasImages);
        slTailleReseauxSociaux = new Slider(15, 60, 30);
        slTailleReseauxSociaux.setLayoutX(200);
        slTailleReseauxSociaux.setLayoutY(160 + iBasImages);
        Label lblOpaciteReseauxSociaux = new Label(rbLocalisation.getString("interface.opaciteReseauxSociaux"));
        lblOpaciteReseauxSociaux.setLayoutX(10);
        lblOpaciteReseauxSociaux.setLayoutY(190 + iBasImages);
        slOpaciteReseauxSociaux = new Slider(0, 1.0, 0.8);
        slOpaciteReseauxSociaux.setLayoutX(200);
        slOpaciteReseauxSociaux.setLayoutY(190 + iBasImages);
        cbReseauxSociauxTwitter = new CheckBox("Twitter");
        cbReseauxSociauxTwitter.setLayoutX(60);
        cbReseauxSociauxTwitter.setLayoutY(220 + iBasImages);
        cbReseauxSociauxTwitter.setSelected(true);
        cbReseauxSociauxGoogle = new CheckBox("Google+");
        cbReseauxSociauxGoogle.setLayoutX(60);
        cbReseauxSociauxGoogle.setLayoutY(250 + iBasImages);
        cbReseauxSociauxGoogle.setSelected(true);
        cbReseauxSociauxFacebook = new CheckBox("Facebook");
        cbReseauxSociauxFacebook.setLayoutX(60);
        cbReseauxSociauxFacebook.setLayoutY(280 + iBasImages);
        cbReseauxSociauxFacebook.setSelected(true);

        cbReseauxSociauxEmail = new CheckBox("Email");
        cbReseauxSociauxEmail.setLayoutX(60);
        cbReseauxSociauxEmail.setLayoutY(310 + iBasImages);
        cbReseauxSociauxEmail.setSelected(true);

        apReseauxSociaux.getChildren().addAll(
                lblTailleReseauxSociaux, slTailleReseauxSociaux,
                lblOpaciteReseauxSociaux, slOpaciteReseauxSociaux,
                cbReseauxSociauxTwitter, cbReseauxSociauxGoogle, cbReseauxSociauxFacebook, cbReseauxSociauxEmail
        );
        apReseauxSociaux.setPrefHeight(0);
        apReseauxSociaux.setMaxHeight(0);
        apReseauxSociaux.setMinHeight(0);
        apReseauxSociaux.setVisible(false);

        lblPanelReseauxSociaux.setOnMouseClicked((me) -> {
            if (apReseauxSociaux.isVisible()) {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(0);
                apReseauxSociaux.setMaxHeight(0);
                apReseauxSociaux.setMinHeight(0);
                apReseauxSociaux.setVisible(false);
            } else {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setVisible(true);
            }
        });
        ivBtnPlusReseauxSociaux.setOnMouseClicked((me) -> {
            if (apReseauxSociaux.isVisible()) {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(0);
                apReseauxSociaux.setMaxHeight(0);
                apReseauxSociaux.setMinHeight(0);
                apReseauxSociaux.setVisible(false);
            } else {
                ivBtnPlusReseauxSociaux.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apReseauxSociaux.setPrefHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMaxHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setMinHeight(taillePanelReseauxSociaux);
                apReseauxSociaux.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Vignettes 
         * ********************************************
         */
        apVignettes = new AnchorPane();

        apVignettes.setLayoutY(40);
        apVignettes.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheVignettes = new CheckBox(rbLocalisation.getString("interface.affichageVignettes"));
        cbAfficheVignettes.setLayoutX(10);
        cbAfficheVignettes.setLayoutY(10);
        apVignettes.getChildren().add(cbAfficheVignettes);
        Label lblPanelVignettes = new Label(rbLocalisation.getString("interface.vignettes"));
        lblPanelVignettes.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelVignettes.setStyle("-fx-background-color : #666");
        lblPanelVignettes.setTextFill(Color.WHITE);
        lblPanelVignettes.setPadding(new Insets(5));
        lblPanelVignettes.setLayoutX(10);
        lblPanelVignettes.setLayoutY(10);
        ImageView ivBtnPlusVignettes = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusVignettes.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusVignettes.setLayoutY(11);

        Label lblChoixCouleurFondVignettes = new Label(rbLocalisation.getString("interface.choixCouleurFondVignettes"));
        lblChoixCouleurFondVignettes.setLayoutX(10);
        lblChoixCouleurFondVignettes.setLayoutY(85);
        cpCouleurFondVignettes = new ColorPicker(Color.valueOf(getStrCouleurFondVignettes()));
        cpCouleurFondVignettes.setLayoutX(200);
        cpCouleurFondVignettes.setLayoutY(83);
        apVignettes.getChildren().addAll(lblChoixCouleurFondVignettes, cpCouleurFondVignettes);
        Label lblChoixCouleurTexteVignettes = new Label(rbLocalisation.getString("interface.choixCouleurTexteVignettes"));
        lblChoixCouleurTexteVignettes.setLayoutX(10);
        lblChoixCouleurTexteVignettes.setLayoutY(115);
        cpCouleurTexteVignettes = new ColorPicker(Color.valueOf(getStrCouleurTexteVignettes()));
        cpCouleurTexteVignettes.setLayoutX(200);
        cpCouleurTexteVignettes.setLayoutY(113);
        apVignettes.getChildren().addAll(lblChoixCouleurTexteVignettes, cpCouleurTexteVignettes);
        Label lblPositVignettes = new Label(rbLocalisation.getString("interface.choixPositVignettes"));
        lblPositVignettes.setLayoutX(10);
        iBasImages = -30;
        lblPositVignettes.setLayoutY(70 + iBasImages);
        apVignettes.getChildren().add(lblPositVignettes);

        rbVignettesLeft = new RadioButton();
        rbVignettesRight = new RadioButton();
        rbVignettesBottom = new RadioButton();

        rbVignettesLeft.setUserData("left");
        rbVignettesRight.setUserData("right");
        rbVignettesBottom.setUserData("bottom");

        rbVignettesLeft.setToggleGroup(tgPosVignettes);
        rbVignettesRight.setToggleGroup(tgPosVignettes);
        rbVignettesBottom.setToggleGroup(tgPosVignettes);

        iPosX = 200;
        iPosY = 70 + iBasImages;

        rbVignettesLeft.setLayoutX(iPosX);
        rbVignettesRight.setLayoutX(iPosX + 40);
        rbVignettesLeft.setLayoutY(iPosY);
        rbVignettesRight.setLayoutY(iPosY);

        rbVignettesBottom.setLayoutX(iPosX + 20);
        rbVignettesBottom.setLayoutY(iPosY + 20);
        apVignettes.getChildren().addAll(
                rbVignettesLeft, rbVignettesRight,
                rbVignettesBottom
        );
        Label lblTailleVignettes = new Label(rbLocalisation.getString("interface.tailleVignettes"));
        lblTailleVignettes.setLayoutX(10);
        lblTailleVignettes.setLayoutY(175 + iBasImages);
        slTailleVignettes = new Slider(50, 300, 120);
        slTailleVignettes.setLayoutX(200);
        slTailleVignettes.setLayoutY(175 + iBasImages);
        Label lblOpaciteVignettes = new Label(rbLocalisation.getString("interface.opaciteVignettes"));
        lblOpaciteVignettes.setLayoutX(10);
        lblOpaciteVignettes.setLayoutY(205 + iBasImages);
        slOpaciteVignettes = new Slider(0, 1.0, 0.8);
        slOpaciteVignettes.setLayoutX(200);
        slOpaciteVignettes.setLayoutY(205 + iBasImages);

        apVignettes.getChildren().addAll(
                lblTailleVignettes, slTailleVignettes,
                lblOpaciteVignettes, slOpaciteVignettes
        );
        apVignettes.setPrefHeight(235 + iBasImages);
        double taillePanelVignettes = apVignettes.getPrefHeight();
        apVignettes.setPrefHeight(0);
        apVignettes.setMaxHeight(0);
        apVignettes.setMinHeight(0);
        apVignettes.setVisible(false);

        lblPanelVignettes.setOnMouseClicked((me) -> {
            if (apVignettes.isVisible()) {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apVignettes.setPrefHeight(0);
                apVignettes.setMaxHeight(0);
                apVignettes.setMinHeight(0);
                apVignettes.setVisible(false);
            } else {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apVignettes.setPrefHeight(taillePanelVignettes);
                apVignettes.setMaxHeight(taillePanelVignettes);
                apVignettes.setMinHeight(taillePanelVignettes);
                apVignettes.setVisible(true);
            }
        });
        ivBtnPlusVignettes.setOnMouseClicked((me) -> {
            if (apVignettes.isVisible()) {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apVignettes.setPrefHeight(0);
                apVignettes.setMaxHeight(0);
                apVignettes.setMinHeight(0);
                apVignettes.setVisible(false);
            } else {
                ivBtnPlusVignettes.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apVignettes.setPrefHeight(taillePanelVignettes);
                apVignettes.setMaxHeight(taillePanelVignettes);
                apVignettes.setMinHeight(taillePanelVignettes);
                apVignettes.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel ComboMenu 
         * ********************************************
         */
        apComboMenu = new AnchorPane();
        apComboMenu.setLayoutY(40);
        apComboMenu.setMinWidth(vbOutils.getPrefWidth() - 20);
        cbAfficheComboMenu = new CheckBox(rbLocalisation.getString("interface.affichageComboMenu"));
        cbAfficheComboMenu.setLayoutX(10);
        cbAfficheComboMenu.setLayoutY(10);
        apComboMenu.getChildren().add(cbAfficheComboMenu);
        cbAfficheComboMenuImages = new CheckBox(rbLocalisation.getString("interface.affichageComboMenuImages"));
        cbAfficheComboMenuImages.setLayoutX(10);
        cbAfficheComboMenuImages.setLayoutY(40);
        cbAfficheComboMenuImages.setSelected(isbAfficheComboMenuImages());
        apComboMenu.getChildren().add(cbAfficheComboMenuImages);
        Label lblPanelComboMenu = new Label(rbLocalisation.getString("interface.comboMenu"));
        lblPanelComboMenu.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelComboMenu.setStyle("-fx-background-color : #666");
        lblPanelComboMenu.setTextFill(Color.WHITE);
        lblPanelComboMenu.setPadding(new Insets(5));
        lblPanelComboMenu.setLayoutX(10);
        lblPanelComboMenu.setLayoutY(10);
        ImageView ivBtnPlusComboMenu = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusComboMenu.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusComboMenu.setLayoutY(11);

        Label lblPositComboMenu = new Label(rbLocalisation.getString("interface.choixPositComboMenu"));
        lblPositComboMenu.setLayoutX(10);
        lblPositComboMenu.setLayoutY(90);
        apComboMenu.getChildren().add(lblPositComboMenu);

        rbComboMenuTopLeft = new RadioButton();
        rbComboMenuTopCenter = new RadioButton();
        rbComboMenuTopRight = new RadioButton();
        rbComboMenuBottomLeft = new RadioButton();
        rbComboMenuBottomCenter = new RadioButton();
        rbComboMenuBottomRight = new RadioButton();

        rbComboMenuTopLeft.setUserData("top:left");
        rbComboMenuTopCenter.setUserData("top:center");
        rbComboMenuTopRight.setUserData("top:right");
        rbComboMenuBottomLeft.setUserData("bottom:left");
        rbComboMenuBottomCenter.setUserData("bottom:center");
        rbComboMenuBottomRight.setUserData("bottom:right");

        rbComboMenuTopLeft.setToggleGroup(tgPosComboMenu);
        rbComboMenuTopCenter.setToggleGroup(tgPosComboMenu);
        rbComboMenuTopRight.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomLeft.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomCenter.setToggleGroup(tgPosComboMenu);
        rbComboMenuBottomRight.setToggleGroup(tgPosComboMenu);

        iPosX = 200;
        iPosY = 70;

        rbComboMenuTopLeft.setLayoutX(iPosX);
        rbComboMenuTopCenter.setLayoutX(iPosX + 20);
        rbComboMenuTopRight.setLayoutX(iPosX + 40);
        rbComboMenuTopLeft.setLayoutY(iPosY);
        rbComboMenuTopCenter.setLayoutY(iPosY);
        rbComboMenuTopRight.setLayoutY(iPosY);

        rbComboMenuBottomLeft.setLayoutX(iPosX);
        rbComboMenuBottomCenter.setLayoutX(iPosX + 20);
        rbComboMenuBottomRight.setLayoutX(iPosX + 40);
        rbComboMenuBottomLeft.setLayoutY(iPosY + 40);
        rbComboMenuBottomCenter.setLayoutY(iPosY + 40);
        rbComboMenuBottomRight.setLayoutY(iPosY + 40);
        apComboMenu.getChildren().addAll(
                rbComboMenuTopLeft, rbComboMenuTopCenter, rbComboMenuTopRight,
                rbComboMenuBottomLeft, rbComboMenuBottomCenter, rbComboMenuBottomRight
        );

        Label lblOffsetXComboMenu = new Label("dX :");
        lblOffsetXComboMenu.setLayoutX(25);
        lblOffsetXComboMenu.setLayoutY(148);
        Label lblOffsetYComboMenu = new Label("dY :");
        lblOffsetYComboMenu.setLayoutX(175);
        lblOffsetYComboMenu.setLayoutY(148);
        bdfOffsetXComboMenu = new BigDecimalField(new BigDecimal(getOffsetXComboMenu()));
        bdfOffsetXComboMenu.setLayoutX(50);
        bdfOffsetXComboMenu.setLayoutY(143);
        bdfOffsetXComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetXComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetXComboMenu.setMaxWidth(100);
        bdfOffsetYComboMenu = new BigDecimalField(new BigDecimal(getOffsetYComboMenu()));
        bdfOffsetYComboMenu.setLayoutX(200);
        bdfOffsetYComboMenu.setLayoutY(143);
        bdfOffsetYComboMenu.setMaxValue(new BigDecimal(2000));
        bdfOffsetYComboMenu.setMinValue(new BigDecimal(0));
        bdfOffsetYComboMenu.setMaxWidth(100);
        apComboMenu.getChildren().addAll(
                lblOffsetXComboMenu, bdfOffsetXComboMenu,
                lblOffsetYComboMenu, bdfOffsetYComboMenu
        );

        apComboMenu.setPrefHeight(235);
        double taillePanelComboMenu = apComboMenu.getPrefHeight();
        apComboMenu.setPrefHeight(0);
        apComboMenu.setMaxHeight(0);
        apComboMenu.setMinHeight(0);
        apComboMenu.setVisible(false);

        lblPanelComboMenu.setOnMouseClicked((me) -> {
            if (apComboMenu.isVisible()) {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(0);
                apComboMenu.setMaxHeight(0);
                apComboMenu.setMinHeight(0);
                apComboMenu.setVisible(false);
            } else {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(taillePanelComboMenu);
                apComboMenu.setMaxHeight(taillePanelComboMenu);
                apComboMenu.setMinHeight(taillePanelComboMenu);
                apComboMenu.setVisible(true);
            }
        });
        ivBtnPlusComboMenu.setOnMouseClicked((me) -> {
            if (apComboMenu.isVisible()) {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(0);
                apComboMenu.setMaxHeight(0);
                apComboMenu.setMinHeight(0);
                apComboMenu.setVisible(false);
            } else {
                ivBtnPlusComboMenu.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apComboMenu.setPrefHeight(taillePanelComboMenu);
                apComboMenu.setMaxHeight(taillePanelComboMenu);
                apComboMenu.setMinHeight(taillePanelComboMenu);
                apComboMenu.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Plan
         * ********************************************
         */
        apPlan = new AnchorPane();

        apPlan.setLayoutY(40);
        apPlan.setPrefHeight(340);
        apPlan.setMinWidth(vbOutils.getPrefWidth() - 20);
        double taillePanelPlan = apPlan.getPrefHeight();
        cbAffichePlan = new CheckBox(rbLocalisation.getString("interface.affichagePlan"));
        cbAffichePlan.setLayoutX(10);
        cbAffichePlan.setLayoutY(10);
        apPlan.getChildren().add(cbAffichePlan);
        Label lblPanelPlan = new Label(rbLocalisation.getString("interface.plan"));
        lblPanelPlan.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelPlan.setStyle("-fx-background-color : #666");
        lblPanelPlan.setTextFill(Color.WHITE);
        lblPanelPlan.setPadding(new Insets(5));
        lblPanelPlan.setLayoutX(10);
        lblPanelPlan.setLayoutY(10);
        ImageView ivBtnPlusPlan = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusPlan.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusPlan.setLayoutY(11);
        Label lblLargeurPlan = new Label(rbLocalisation.getString("interface.largeurPlan"));
        lblLargeurPlan.setLayoutX(10);
        lblLargeurPlan.setLayoutY(40);
        slLargeurPlan = new Slider(200, 800, 300);
        slLargeurPlan.setLayoutX(200);
        slLargeurPlan.setLayoutY(40);
        Label lblPositPlan = new Label(rbLocalisation.getString("interface.positionPlan"));
        lblPositPlan.setLayoutX(10);
        lblPositPlan.setLayoutY(70);
        rbPlanLeft = new RadioButton("");
        rbPlanLeft.setLayoutX(200);
        rbPlanLeft.setLayoutY(70);
        rbPlanLeft.setUserData("left");
        rbPlanLeft.setToggleGroup(tgPosPlan);
        rbPlanRight = new RadioButton("");
        rbPlanRight.setLayoutX(230);
        rbPlanRight.setLayoutY(70);
        rbPlanRight.setUserData("right");
        rbPlanRight.setToggleGroup(tgPosPlan);
        Label lblCouleurFondPlan = new Label(rbLocalisation.getString("interface.couleurFondPlan"));
        lblCouleurFondPlan.setLayoutX(10);
        lblCouleurFondPlan.setLayoutY(100);
        cpCouleurFondPlan = new ColorPicker(getCouleurFondPlan());
        cpCouleurFondPlan.setLayoutX(200);
        cpCouleurFondPlan.setLayoutY(95);
        Label lblCouleurTextePlan = new Label(rbLocalisation.getString("interface.couleurTextePlan"));
        lblCouleurTextePlan.setLayoutX(10);
        lblCouleurTextePlan.setLayoutY(130);
        cpCouleurTextePlan = new ColorPicker(getCouleurTextePlan());
        cpCouleurTextePlan.setLayoutX(200);
        cpCouleurTextePlan.setLayoutY(125);
        Label lblOpacitePlan = new Label(rbLocalisation.getString("interface.opacitePlan"));
        lblOpacitePlan.setLayoutX(10);
        lblOpacitePlan.setLayoutY(160);
        slOpacitePlan = new Slider(0, 1.0, 0.8);
        slOpacitePlan.setLayoutX(200);
        slOpacitePlan.setLayoutY(160);
        cbAfficheRadar = new CheckBox(rbLocalisation.getString("interface.afficheRadar"));
        cbAfficheRadar.setLayoutX(10);
        cbAfficheRadar.setLayoutY(190);

        Label lblTailleRadar = new Label(rbLocalisation.getString("interface.tailleRadar"));
        lblTailleRadar.setLayoutX(10);
        lblTailleRadar.setLayoutY(220);
        slTailleRadar = new Slider(0, 80, getTailleRadar());
        slTailleRadar.setLayoutX(200);
        slTailleRadar.setLayoutY(220);
        Label lblOpaciteRadar = new Label(rbLocalisation.getString("interface.opaciteRadar"));
        lblOpaciteRadar.setLayoutX(10);
        lblOpaciteRadar.setLayoutY(250);
        slOpaciteRadar = new Slider(0, 1, 0.8);
        slOpaciteRadar.setLayoutX(200);
        slOpaciteRadar.setLayoutY(250);
        Label lblCouleurFondRadar = new Label(rbLocalisation.getString("interface.couleurFondRadar"));
        lblCouleurFondRadar.setLayoutX(10);
        lblCouleurFondRadar.setLayoutY(280);
        cpCouleurFondRadar = new ColorPicker(getCouleurFondRadar());
        cpCouleurFondRadar.setLayoutX(200);
        cpCouleurFondRadar.setLayoutY(280);
        Label lblCouleurLigneRadar = new Label(rbLocalisation.getString("interface.couleurLigneRadar"));
        lblCouleurLigneRadar.setLayoutX(10);
        lblCouleurLigneRadar.setLayoutY(310);
        cpCouleurLigneRadar = new ColorPicker(getCouleurLigneRadar());
        cpCouleurLigneRadar.setLayoutX(200);
        cpCouleurLigneRadar.setLayoutY(310);
        apPlan.getChildren().addAll(
                lblLargeurPlan, slLargeurPlan,
                lblPositPlan, rbPlanLeft, rbPlanRight,
                lblCouleurFondPlan, cpCouleurFondPlan,
                lblCouleurTextePlan, cpCouleurTextePlan,
                lblOpacitePlan, slOpacitePlan,
                cbAfficheRadar,
                lblTailleRadar, slTailleRadar,
                lblOpaciteRadar, slOpaciteRadar,
                lblCouleurFondRadar, cpCouleurFondRadar,
                lblCouleurLigneRadar, cpCouleurLigneRadar
        );

        apPlan.setPrefHeight(0);
        apPlan.setMaxHeight(0);
        apPlan.setMinHeight(0);
        apPlan.setVisible(false);
        lblPanelPlan.setOnMouseClicked((me) -> {
            if (apPlan.isVisible()) {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apPlan.setPrefHeight(0);
                apPlan.setMaxHeight(0);
                apPlan.setMinHeight(0);
                apPlan.setVisible(false);
            } else {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apPlan.setPrefHeight(taillePanelPlan);
                apPlan.setMaxHeight(taillePanelPlan);
                apPlan.setMinHeight(taillePanelPlan);
                apPlan.setVisible(true);
            }
        });
        ivBtnPlusPlan.setOnMouseClicked((me) -> {
            if (apPlan.isVisible()) {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apPlan.setPrefHeight(0);
                apPlan.setMaxHeight(0);
                apPlan.setMinHeight(0);
                apPlan.setVisible(false);
            } else {
                ivBtnPlusPlan.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apPlan.setPrefHeight(taillePanelPlan);
                apPlan.setMaxHeight(taillePanelPlan);
                apPlan.setMinHeight(taillePanelPlan);
                apPlan.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Image Fond
         * ********************************************
         */
        apImageFond = new AnchorPane();

        apImageFond.setLayoutY(40);
        apImageFond.setPrefHeight(60);
        apImageFond.setMinWidth(vbOutils.getPrefWidth() - 20);
        taillePanelImageFond = apImageFond.getPrefHeight();
        Label lblPanelImageFond = new Label(rbLocalisation.getString("interface.imageFond"));
        lblPanelImageFond.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelImageFond.setStyle("-fx-background-color : #666");
        lblPanelImageFond.setTextFill(Color.WHITE);
        lblPanelImageFond.setPadding(new Insets(5));
        lblPanelImageFond.setLayoutX(10);
        lblPanelImageFond.setLayoutY(10);
        ImageView ivBtnPlusImageFond = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusImageFond.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusImageFond.setLayoutY(11);
        apImageFond.getChildren().addAll();
        Image imgAjoute = new Image("file:" + strRepertAppli + File.separator + "images/ajoute.png", 30, 30, true, true);
        Button btnAjouteImage = new Button(rbLocalisation.getString("interface.imageFondAjoute"), new ImageView(imgAjoute));
        btnAjouteImage.setLayoutX(10);
        btnAjouteImage.setLayoutY(10);
        apImageFond.getChildren().addAll(btnAjouteImage);
        btnAjouteImage.setOnMouseClicked(
                (me) -> {
                    ajoutImageFond();
                }
        );
        apImageFond.setPrefHeight(0);
        apImageFond.setMaxHeight(0);
        apImageFond.setMinHeight(0);
        apImageFond.setVisible(false);
        lblPanelImageFond.setOnMouseClicked((me) -> {
            if (apImageFond.isVisible()) {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apImageFond.setPrefHeight(0);
                apImageFond.setMaxHeight(0);
                apImageFond.setMinHeight(0);
                apImageFond.setVisible(false);
            } else {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apImageFond.setPrefHeight(taillePanelImageFond);
                apImageFond.setMaxHeight(taillePanelImageFond);
                apImageFond.setMinHeight(taillePanelImageFond);
                apImageFond.setVisible(true);
            }
        });
        ivBtnPlusImageFond.setOnMouseClicked((me) -> {
            if (apImageFond.isVisible()) {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apImageFond.setPrefHeight(0);
                apImageFond.setMaxHeight(0);
                apImageFond.setMinHeight(0);
                apImageFond.setVisible(false);
            } else {
                ivBtnPlusImageFond.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apImageFond.setPrefHeight(taillePanelImageFond);
                apImageFond.setMaxHeight(taillePanelImageFond);
                apImageFond.setMinHeight(taillePanelImageFond);
                apImageFond.setVisible(true);
            }
        });

        /*
         * ********************************************
         *     Panel Menu contextuel
         * ********************************************
         */
        apMenuContextuel = new AnchorPane();

        apMenuContextuel.setLayoutY(40);
        apMenuContextuel.setPrefHeight(280);
        apMenuContextuel.setMinWidth(vbOutils.getPrefWidth() - 20);
        double taillePanelMenuContextuel = apMenuContextuel.getPrefHeight();
        Label lblPanelMenuContextuel = new Label(rbLocalisation.getString("interface.menuContextuel"));
        lblPanelMenuContextuel.setPrefWidth(vbOutils.getPrefWidth());
        lblPanelMenuContextuel.setStyle("-fx-background-color : #666");
        lblPanelMenuContextuel.setTextFill(Color.WHITE);
        lblPanelMenuContextuel.setPadding(new Insets(5));
        lblPanelMenuContextuel.setLayoutX(10);
        lblPanelMenuContextuel.setLayoutY(10);
        ImageView ivBtnPlusMenuContextuel = new ImageView(new Image("file:" + "images/plus.png", 20, 20, true, true));
        ivBtnPlusMenuContextuel.setLayoutX(vbOutils.getPrefWidth() - 20);
        ivBtnPlusMenuContextuel.setLayoutY(11);
        cbAfficheMenuContextuel = new CheckBox(rbLocalisation.getString("interface.affichageMenuContextuel"));
        cbAfficheMenuContextuel.setLayoutX(10);
        cbAfficheMenuContextuel.setLayoutY(10);
        cbAfficheMenuContextuel.setSelected(isbAfficheMenuContextuel());
        cbAffichePrecSuivMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelSuivPrec"));
        cbAffichePrecSuivMC.setLayoutX(10);
        cbAffichePrecSuivMC.setLayoutY(40);
        cbAffichePrecSuivMC.setSelected(isbAffichePrecSuivMC());
        cbAffichePlanetNormalMC = new CheckBox(rbLocalisation.getString("interface.menuContextuelPlaneteNormal"));
        cbAffichePlanetNormalMC.setLayoutX(10);
        cbAffichePlanetNormalMC.setLayoutY(70);
        cbAffichePlanetNormalMC.setSelected(isbAffichePlanetNormalMC());
        cbAffichePersMC1 = new CheckBox(rbLocalisation.getString("interface.menuContextuelPers1"));
        cbAffichePersMC1.setLayoutX(10);
        cbAffichePersMC1.setLayoutY(100);
        cbAffichePersMC1.setSelected(isbAffichePersMC1());
        Label lblPersLib1 = new Label(rbLocalisation.getString("interface.menuContextuelLib"));
        lblPersLib1.setLayoutX(10);
        lblPersLib1.setLayoutY(130);
        lblPersLib1.setDisable(true);
        tfPersLib1 = new TextField();
        tfPersLib1.setLayoutX(120);
        tfPersLib1.setLayoutY(130);
        tfPersLib1.setPrefSize(220, 20);
        tfPersLib1.setDisable(true);
        Label lblPersUrl1 = new Label(rbLocalisation.getString("interface.menuContextuelURL"));
        lblPersUrl1.setLayoutX(10);
        lblPersUrl1.setLayoutY(160);
        lblPersUrl1.setDisable(true);
        tfPersURL1 = new TextField();
        tfPersURL1.setLayoutX(120);
        tfPersURL1.setLayoutY(160);
        tfPersURL1.setPrefSize(220, 20);
        tfPersURL1.setDisable(true);
        cbAffichePersMC2 = new CheckBox(rbLocalisation.getString("interface.menuContextuelPers2"));
        cbAffichePersMC2.setLayoutX(10);
        cbAffichePersMC2.setLayoutY(190);
        cbAffichePersMC2.setDisable(true);
        Label lblPersLib2 = new Label(rbLocalisation.getString("interface.menuContextuelLib"));
        lblPersLib2.setLayoutX(10);
        lblPersLib2.setLayoutY(220);
        lblPersLib2.setDisable(true);
        tfPersLib2 = new TextField();
        tfPersLib2.setLayoutX(120);
        tfPersLib2.setLayoutY(220);
        tfPersLib2.setPrefSize(220, 20);
        tfPersLib2.setDisable(true);
        Label lblPersUrl2 = new Label(rbLocalisation.getString("interface.menuContextuelURL"));
        lblPersUrl2.setLayoutX(10);
        lblPersUrl2.setLayoutY(250);
        lblPersUrl2.setDisable(true);
        tfPersURL2 = new TextField();
        tfPersURL2.setLayoutX(120);
        tfPersURL2.setLayoutY(250);
        tfPersURL2.setPrefSize(220, 20);
        tfPersURL2.setDisable(true);
        apMenuContextuel.getChildren().addAll(
                cbAfficheMenuContextuel,
                cbAffichePrecSuivMC,
                cbAffichePlanetNormalMC,
                cbAffichePersMC1,
                lblPersLib1, tfPersLib1,
                lblPersUrl1, tfPersURL1,
                cbAffichePersMC2,
                lblPersLib2, tfPersLib2,
                lblPersUrl2, tfPersURL2
        );

        apMenuContextuel.setPrefHeight(0);
        apMenuContextuel.setMaxHeight(0);
        apMenuContextuel.setMinHeight(0);
        apMenuContextuel.setVisible(false);
        lblPanelMenuContextuel.setOnMouseClicked((me) -> {
            if (apMenuContextuel.isVisible()) {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(0);
                apMenuContextuel.setMaxHeight(0);
                apMenuContextuel.setMinHeight(0);
                apMenuContextuel.setVisible(false);
            } else {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMaxHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMinHeight(taillePanelMenuContextuel);
                apMenuContextuel.setVisible(true);
            }
        });
        ivBtnPlusMenuContextuel.setOnMouseClicked((me) -> {
            if (apMenuContextuel.isVisible()) {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/plus.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(0);
                apMenuContextuel.setMaxHeight(0);
                apMenuContextuel.setMinHeight(0);
                apMenuContextuel.setVisible(false);
            } else {
                ivBtnPlusMenuContextuel.setImage(new Image("file:" + "images/moins.png", 20, 20, true, true));
                apMenuContextuel.setPrefHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMaxHeight(taillePanelMenuContextuel);
                apMenuContextuel.setMinHeight(taillePanelMenuContextuel);
                apMenuContextuel.setVisible(true);
            }
        });

        /*
         * *****************************************************
         * Style des Pannels
         * *****************************************************
         */
//        String styleap = "-fx-background-color : #ccc;";
        String strStyleAp = "";
        apBoussole.setStyle(strStyleAp);
        apBarreClassique.setStyle(strStyleAp);
        apTitre.setStyle(strStyleAp);
        apDiapo.setStyle(strStyleAp);
        apHotSpots.setStyle(strStyleAp);
        apMasque.setStyle(strStyleAp);
        apReseauxSociaux.setStyle(strStyleAp);
        apVignettes.setStyle(strStyleAp);
        apComboMenu.setStyle(strStyleAp);
        apPlan.setStyle(strStyleAp);
        apMenuContextuel.setStyle(strStyleAp);
        apImageFond.setStyle(strStyleAp);
        apBoussole.setLayoutX(20);
        apBarreClassique.setLayoutX(20);
        apTitre.setLayoutX(20);
        apDiapo.setLayoutX(20);
        apHotSpots.setLayoutX(20);
        apMasque.setLayoutX(20);
        apReseauxSociaux.setLayoutX(20);
        apVignettes.setLayoutX(20);
        apComboMenu.setLayoutX(20);
        apPlan.setLayoutX(20);
        apMenuContextuel.setLayoutX(20);
        apImageFond.setLayoutX(20);
        /*
         * *******************************************************
         *     Ajout des Elements dans les Pannels
         * *******************************************************
         */
        apTIT.getChildren().addAll(apTitre, lblPanelTitre, ivBtnPlusTitre);
        apECR.getChildren().addAll(apFenetre, lblPanelFenetre, ivBtnPlusFenetre);
        apDIA.getChildren().addAll(apDiapo, lblDiaporama, ivBtnDiaporama);
        apCLASS.getChildren().addAll(apBarreClassique, lblBarreClassique, ivBtnPlusBarreClassique);
        apPERS.getChildren().addAll(apBarrePersonnalisee, lblBarrePersonnalisee, ivBtnPlusBarrePersonnalisee);
        apHS.getChildren().addAll(lblChoixHS, apHotSpots, ivBtnPlusHS);
        apBOUSS.getChildren().addAll(apBoussole, lblPanelBoussole, ivBtnPlusBouss);
        apMASQ.getChildren().addAll(apMasque, lblPanelMasque, ivBtnPlusMasque);
        apRS.getChildren().addAll(apReseauxSociaux, lblPanelReseauxSociaux, ivBtnPlusReseauxSociaux);
        apVIG.getChildren().addAll(apVignettes, lblPanelVignettes, ivBtnPlusVignettes);
        apCBM.getChildren().addAll(apComboMenu, lblPanelComboMenu, ivBtnPlusComboMenu);
        apPLAN.getChildren().addAll(apPlan, lblPanelPlan, ivBtnPlusPlan);
        apIF.getChildren().addAll(apImageFond, lblPanelImageFond, ivBtnPlusImageFond);
        apMC.getChildren().addAll(apMenuContextuel, lblPanelMenuContextuel, ivBtnPlusMenuContextuel);

        /*
         * ******************************************************
         *     Ajouts des pannels dans la barre d'outils
         * ******************************************************
         */
        vbOutils.getChildren().addAll(
                apCoulTheme,
                apTIT,
                apECR,
                apDIA,
                apCLASS,
                apPERS,
                apPLAN,
                apHS,
                apBOUSS,
                apMASQ,
                apRS,
                apVIG,
                apCBM,
                apMC,
                apIF
        );

        /*
         * *******************************************************
         *     Ajout des ecouteurs sur les différents éléments
         * ******************************************************
         */
        tgImage.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgImage.getSelectedToggle() != null) {
                cbImage.setDisable(true);
                Rectangle2D viewportRect;
                switch (tgImage.getSelectedToggle().getUserData().toString()) {
                    case "claire":
                        viewportRect = new Rectangle2D(0, 0, imgClaire.getWidth(), imgClaire.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imgClaire);
                        break;
                    case "sombre":
                        viewportRect = new Rectangle2D(0, 0, imgSombre.getWidth(), imgSombre.getHeight());
                        ivVisualisation.setViewport(viewportRect);
                        ivVisualisation.setImage(imgSombre);
                        break;
                    case "perso":
                        cbImage.setDisable(false);
                        int index = cbImage.getSelectionModel().getSelectedIndex();
                        if (index != -1) {
                            afficheImage(index);
                        }
                        break;
                }
            }
        });

        cbImage.valueProperty().addListener((ov, t, t1) -> {
            int index = cbImage.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                afficheImage(index);
            }
        });

        /*
         Listeners Couleur Thème
         */
        cpCouleurTheme.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            couleurTheme = cpCouleurTheme.getValue();
            String strCoul1 = cpCouleurTheme.getValue().toString().substring(2, 8);
            couleurHotspots = couleurTheme;
            couleurBarreClassique = couleurTheme;
            couleurMasque = couleurTheme;
            setCouleurFondPlan(couleurTheme);
            couleurBarrePersonnalisee = couleurTheme;
            setStrCouleurFondPlan(getCouleurFondPlan().toString().substring(2, 8));
            affichePlan();
            changeCouleurTitre(strCoul1);
            changeCouleurVignettes(strCoul1);
            //changeCouleur(hue);
            cpCouleurFondTitre.setValue(couleurTheme);
            cpCouleurFondPlan.setValue(couleurTheme);
            cpCouleurHotspots.setValue(couleurTheme);
            cpCouleurBarreClassique.setValue(couleurTheme);
            cpCouleurMasques.setValue(couleurTheme);
            cpCouleurBarrePersonnalisee.setValue(couleurTheme);
            cpCouleurFondVignettes.setValue(Color.hsb(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness()));
            changeCouleurBarreClassique(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurHS(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurHSPhoto(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            changeCouleurMasque(couleurTheme.getHue(), couleurTheme.getSaturation(), couleurTheme.getBrightness());
            afficheBarrePersonnalisee();
        });

        /*
         Listeners HotSpots
         */
        cpCouleurHotspots.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }

            couleurHotspots = cpCouleurHotspots.getValue();
            changeCouleurHS(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
        });
        cpCouleurHotspotsPhoto.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            couleurHotspotsPhoto = cpCouleurHotspotsPhoto.getValue();
            changeCouleurHSPhoto(couleurHotspotsPhoto.getHue(), couleurHotspotsPhoto.getSaturation(), couleurHotspotsPhoto.getBrightness());
        });

        /*
         Listeners Titre
         */
        cbListePolices.valueProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setStrTitrePoliceNom(new_val.toString());
                Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
                lblTxtTitre.setFont(fonte1);
                lblTxtTitre.setPrefHeight(-1);
                afficheVignettes();
                affichePlan();
            }
        });
        cbAfficheTitre.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAfficheTitre((boolean) new_val);
            apVisualisation.getChildren().remove(hbbarreBoutons);
            apVisualisation.getChildren().remove(ivHotSpot);
            apVisualisation.getChildren().remove(ivHotSpotImage);
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            afficheVignettes();
            affichePlan();
        });

        slTaillePolice.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                double taille = (double) newValue;
                setStrTitrePoliceTaille(Integer.toString((int) Math.round(taille)));
                Font fonte1 = Font.font(getStrTitrePoliceNom(), Double.parseDouble(getStrTitrePoliceTaille()));
                lblTxtTitre.setFont(fonte1);
                lblTxtTitre.setPrefHeight(-1);
                afficheVignettes();
                affichePlan();
            }
        });
        slOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setTitreOpacite((double) newValue);
                String strCoul = cpCouleurFondTitre.getValue().toString().substring(2, 8);
                Color couleur = cpCouleurFondTitre.getValue();
                int iRouge = (int) (couleur.getRed() * 255.d);
                int iBleu = (int) (couleur.getBlue() * 255.d);
                int iVert = (int) (couleur.getGreen() * 255.d);
                String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";
                lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond);
            }
        });
        slTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setTitreTaille((double) newValue);
                double taille = (double) getTitreTaille() / 100.d * ivVisualisation.getFitWidth();
                lblTxtTitre.setMinWidth(taille);
                lblTxtTitre.setLayoutX(ivVisualisation.getLayoutX() + (ivVisualisation.getFitWidth() - lblTxtTitre.getMinWidth()) / 2);
            }
        });
        cpCouleurTitre.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurTitre.getValue().toString().substring(2, 8);
            setStrCouleurTitre("#" + strCoul);
            lblTxtTitre.setTextFill(Color.valueOf(getStrCouleurTitre()));
        });
        cpCouleurFondTitre.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCouleur = cpCouleurFondTitre.getValue().toString().substring(2, 8);
            Color couleur = cpCouleurFondTitre.getValue();
            setStrCouleurFondTitre("#" + strCouleur);
            int iRouge = (int) (couleur.getRed() * 255.d);
            int iBleu = (int) (couleur.getBlue() * 255.d);
            int iVert = (int) (couleur.getGreen() * 255.d);
            String strCoulFond = "rgba(" + iRouge + "," + iVert + "," + iBleu + "," + getTitreOpacite() + ")";

            lblTxtTitre.setStyle("-fx-background-color : " + strCoulFond);
        });
        /*
         Listeners Fenêtres
         */
        cbFenetreInfoPersonnalise.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbFenetreInfoPersonnalise((boolean) new_val);
            apFenetreInfoPers.setDisable(!isbFenetreInfoPersonnalise());
            afficheFenetreInfo();
        });

        cbFenetreAidePersonnalise.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbFenetreAidePersonnalise((boolean) new_val);
            apFenetreAidePers.setDisable(!isbFenetreAidePersonnalise());
            afficheFenetreAide();
        });

        cbAfficheFenetreInfo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            bAfficheFenetreInfo = new_val;
            if (new_val == true) {
                cbAfficheFenetreAide.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreInfo();
        });

        cbAfficheFenetreAide.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            bAfficheFenetreAide = new_val;
            if (new_val == true) {
                cbAfficheFenetreInfo.setSelected(false);
                cbVisualiseDiapo.setSelected(false);
            }
            afficheFenetreAide();
        });

        btnFenetreInfo.setOnMouseClicked((me) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrFenetreInfoImage(ajoutFenetreImage());
            if (!strFenetreInfoImage.equals("")) {
                tfFenetreInfoImage.setText(getStrFenetreInfoImage());
            }
            afficheFenetreInfo();
        }
        );

        btnFenetreAide.setOnMouseClicked((me) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrFenetreAideImage(ajoutFenetreImage());
            if (!strFenetreAideImage.equals("")) {
                tfFenetreAideImage.setText(getStrFenetreAideImage());
            }
            afficheFenetreAide();
        }
        );
        slFenetreInfoTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setFenetreInfoTaille(taille);
                afficheFenetreInfo();
            }
        });
        slFenetreInfoOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setFenetreInfoOpacite(opac);
                afficheFenetreInfo();
            }
        });
        bdfFenetreInfoPosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreInfoPosX(new_value.doubleValue());
            afficheFenetreInfo();
        });
        bdfFenetreInfoPosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreInfoPosY(new_value.doubleValue());
            afficheFenetreInfo();
        });

        tfFenetreURL.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrFenetreURL(newValue);
        });

        tfFenetreTexteURL.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrFenetreTexteURL(newValue);
            afficheFenetreInfo();
        });

        slFenetrePoliceTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                double taille = Math.round((double) newValue * 10.d) / 10.d;
                setFenetrePoliceTaille(taille);
                afficheFenetreInfo();
            }
        });

        cpFenetreURLCouleur.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrFenetreURLCouleur("#" + cpFenetreURLCouleur.getValue().toString().substring(2, 8));
            afficheFenetreInfo();
        });

        bdfFenetreURLPosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreURLPosX(new_value.doubleValue());
            afficheFenetreInfo();
        });

        bdfFenetreURLPosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreURLPosY(new_value.doubleValue());
            afficheFenetreInfo();
        });
        slFenetreAideTaille.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                double taille = (double) newValue;
                setFenetreAideTaille(taille);
                afficheFenetreAide();
            }
        });
        slFenetreAideOpacite.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                double opac = (double) newValue;
                setFenetreAideOpacite(opac);
                afficheFenetreAide();
            }
        });

        bdfFenetreAidePosX.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreAidePosX(new_value.doubleValue());
            afficheFenetreAide();
        });
        bdfFenetreAidePosY.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setFenetreAidePosY(new_value.doubleValue());
            afficheFenetreAide();
        });


        /*
         Listeners Diaporama
         */
        slOpaciteDiaporama.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setDiaporamaOpacite((double) newValue);
                afficheDiaporama();
            }
        });

        cpCouleurDiaporama.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurDiaporama.getValue().toString().substring(2, 8);
            setStrCouleurDiaporama("#" + strCoul);
            afficheDiaporama();
        });

        cbVisualiseDiapo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            bVisualiseDiaporama = new_val;
            afficheDiaporama();
            if (new_val == true) {
                cbAfficheFenetreInfo.setSelected(false);
                cbAfficheFenetreAide.setSelected(false);
            }
        });
        /*
         Listeners Barre de navigation Classique
         */
        slEspacementBarreClassique.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setEspacementBarreClassique((double) newValue);
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });

        cblisteStyleBarreClassique.valueProperty().addListener((ov, t, t1) -> {
            if (t1 != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setStyleBarreClassique(t1.toString());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            }
        });

        bdfOffsetXBarreClassique.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }

            setOffsetXBarreClassique(new_value.doubleValue());
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        bdfOffsetYBarreClassique.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetYBarreClassique(new_value.doubleValue());
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });

        tgPositionBarreClassique.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (tgPositionBarreClassique.getSelectedToggle() != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setStrPositionBarreClassique(tgPositionBarreClassique.getSelectedToggle().getUserData().toString());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            }
        });
        cbBarreClassiqueVisible.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrVisibiliteBarreClassique("oui");
                cbBarrePersonnaliseeVisible.setSelected(false);
            } else {
                setStrVisibiliteBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbSuivantPrecedent.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbSuivantPrecedent((boolean) new_val);
            vbFondSuivant.setVisible(isbSuivantPrecedent());
            vbFondPrecedent.setVisible(isbSuivantPrecedent());
        });
        cpCouleurBarreClassique.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            couleurBarreClassique = cpCouleurBarreClassique.getValue();
            changeCouleurBarreClassique(couleurBarreClassique.getHue(), couleurBarreClassique.getSaturation(), couleurBarreClassique.getBrightness());
        });
        cbDeplacementsBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrDeplacementsBarreClassique("oui");
            } else {
                setStrDeplacementsBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbZoomBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrZoomBarreClassique("oui");
            } else {
                setStrZoomBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbOutilsBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrOutilsBarreClassique("oui");
            } else {
                setStrOutilsBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbSourisBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrSourisBarreClassique("oui");
            } else {
                setStrSourisBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbRotationBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrRotationBarreClassique("oui");
            } else {
                setStrRotationBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        cbFSBarreClassique.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrPleinEcranBarreClassique("oui");
            } else {
                setStrPleinEcranBarreClassique("non");
            }
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
        });
        /*
         Listeners Barre de navigation Personnalisee
         */

        bdfOffsetXBarrePersonnalisee.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetXBarrePersonnalisee(new_value.doubleValue());
            afficheBarrePersonnalisee();
        });
        bdfOffsetYBarrePersonnalisee.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetYBarrePersonnalisee(new_value.doubleValue());
            afficheBarrePersonnalisee();
        });

        grpCouleurBarrePersonnalisee.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (grpCouleurBarrePersonnalisee.getSelectedToggle() != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setbCouleurOrigineBarrePersonnalisee((boolean) grpCouleurBarrePersonnalisee.getSelectedToggle().getUserData());
                cpCouleurBarrePersonnalisee.setDisable(isbCouleurOrigineBarrePersonnalisee());
                afficheBarrePersonnalisee();
            }
        });

        grpPositionBarrePersonnalisee.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (grpPositionBarrePersonnalisee.getSelectedToggle() != null) {
                if (iNombrePanoramiques != 0) {
                    bDejaSauve = false;
                    stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                }
                setStrPositionBarrePersonnalisee(grpPositionBarrePersonnalisee.getSelectedToggle().getUserData().toString());
                afficheBarrePersonnalisee();
            }
        });
        cbBarrePersonnaliseeVisible.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrVisibiliteBarrePersonnalisee("oui");
                cbBarreClassiqueVisible.setSelected(false);
            } else {
                setStrVisibiliteBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbSuivantPrecedent.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbSuivantPrecedent((boolean) new_val);
            vbFondSuivant.setVisible(isbSuivantPrecedent());
            vbFondPrecedent.setVisible(isbSuivantPrecedent());
        });
        cpCouleurBarrePersonnalisee.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            couleurBarrePersonnalisee = cpCouleurBarrePersonnalisee.getValue();
            afficheBarrePersonnalisee();
        });
        cbDeplacementsBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrDeplacementsBarrePersonnalisee("oui");
            } else {
                setStrDeplacementsBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbZoomBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrZoomBarrePersonnalisee("oui");
            } else {
                setStrZoomBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbSourisBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrSourisBarrePersonnalisee("oui");
            } else {
                setStrSourisBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbRotationBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrRotationBarrePersonnalisee("oui");
            } else {
                setStrRotationBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        cbFSBarrePersonnalisee.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val) {
                setStrPleinEcranBarrePersonnalisee("oui");
            } else {
                setStrPleinEcranBarrePersonnalisee("non");
            }
            afficheBarrePersonnalisee();
        });
        sltailleBarrePersonnalisee.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleBarrePersonnalisee(taille);
                afficheBarrePersonnalisee();
            }
        });
        sltailleIconesBarrePersonnalisee.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleIconesBarrePersonnalisee(taille);
                afficheBarrePersonnalisee();
            }
        });
        btnLienBarrePersonnalisee.setOnMouseClicked(
                (me) -> {
                    if (iNombrePanoramiques != 0) {
                        bDejaSauve = false;
                        stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    }
                    try {
                        choixBarrePersonnalisee();
                    } catch (IOException ex) {
                        Logger.getLogger(GestionnaireInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        btnEditerBarre.setOnMouseClicked(
                (me) -> {
                    if (iNombrePanoramiques != 0) {
                        bDejaSauve = false;
                        stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
                    }
                    EditeurPanovisu.creerEditerBarre(tfLienImageBarrePersonnalisee.getText());
                }
        );
        tfLien1BarrePersonnalisee.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrLien1BarrePersonnalisee(tfLien1BarrePersonnalisee.getText());
        });
        tfLien2BarrePersonnalisee.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrLien2BarrePersonnalisee(tfLien2BarrePersonnalisee.getText());
        });


        /*
         Listeners Boussole
         */
        tgPosBouss.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosBouss.getSelectedToggle() != null) {
                setStrPositionBoussole(tgPosBouss.getSelectedToggle().getUserData().toString());
                afficheBoussole();
            }
        });
        bdfOffsetXBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetXBoussole(new_value.doubleValue());
            afficheBoussole();
        });
        bdfOffsetYBoussole.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetYBoussole(new_value.doubleValue());
            afficheBoussole();
        });
        slTailleBoussole.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleBoussole(taille);
                afficheBoussole();
            }
        });
        cbAfficheBoussole.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheBoussole((boolean) new_val);
                afficheBoussole();
            }
        });
        cbAiguilleMobile.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAiguilleMobileBoussole((boolean) new_val);
            }
        });

        /*
         Listeners Bouton de Masquage
         */
        slOpaciteBoussole.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteBoussole(opac);
                afficheBoussole();
            }
        });

        tgPosMasque.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosMasque.getSelectedToggle() != null) {
                setStrPositionMasque(tgPosMasque.getSelectedToggle().getUserData().toString());
                apVisualisation.getChildren().remove(ivMasque);
                afficheMasque();
            }
        });
        bdfOffsetXMasque.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setdXMasque(new_value.doubleValue());
            afficheMasque();
        });
        bdfOffsetYMasque.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setdYMasque(new_value.doubleValue());
            afficheMasque();
        });
        slTailleMasque.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleMasque(taille);
                afficheMasque();
            }
        });
        slOpaciteMasque.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteMasque(opac);
                afficheMasque();
            }
        });
        cbMasqueNavigation.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueNavigation((boolean) new_val);
            }
        });
        cpCouleurMasques.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            couleurMasque = cpCouleurMasques.getValue();
            changeCouleurMasque(couleurMasque.getHue(), couleurMasque.getSaturation(), couleurMasque.getBrightness());
        });
        cbAfficheMasque.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheMasque((boolean) new_val);
                changeCouleurMasque(couleurHotspots.getHue(), couleurHotspots.getSaturation(), couleurHotspots.getBrightness());
                afficheMasque();
            }
        });
        cbMasqueBoussole.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueBoussole((boolean) new_val);
            }
        });
        cbMasqueTitre.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueTitre((boolean) new_val);
            }
        });
        cbMasquePlan.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasquePlan((boolean) new_val);
            }
        });
        cbMasqueVignettes.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueVignettes((boolean) new_val);
            }
        });
        cbMasqueCombo.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueCombo((boolean) new_val);
            }
        });
        cbMasqueSuivPrec.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueSuivPrec((boolean) new_val);
            }
        });
        cbMasqueHotspots.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbMasqueHotspots((boolean) new_val);
            }
        });

        /*
         Listeners Reseaux Sociaux
         */
        bdfOffsetXReseauxSociaux.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setdXReseauxSociaux(new_value.doubleValue());
            afficheReseauxSociaux();
        });

        bdfOffsetYreseauxSociaux.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setdYReseauxSociaux(new_value.doubleValue());
            afficheReseauxSociaux();
        });

        tgPosReseauxSociaux.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosReseauxSociaux.getSelectedToggle() != null) {
                setStrPositionReseauxSociaux(tgPosReseauxSociaux.getSelectedToggle().getUserData().toString());
                afficheReseauxSociaux();
            }
        });
        slTailleReseauxSociaux.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleReseauxSociaux(taille);
                afficheReseauxSociaux();
            }
        });
        slOpaciteReseauxSociaux.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteReseauxSociaux(opac);
                afficheReseauxSociaux();
            }
        });
        cbAfficheReseauxSociaux.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheReseauxSociaux((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxTwitter.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxTwitter((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxGoogle.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxGoogle((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxFacebook.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxFacebook((boolean) new_val);
                afficheReseauxSociaux();
            }
        });
        cbReseauxSociauxEmail.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbReseauxSociauxEmail((boolean) new_val);
                afficheReseauxSociaux();
            }
        });

        /*
         Listeners Vignettes
         */
        tgPosVignettes.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosVignettes.getSelectedToggle() != null) {
                setStrPositionVignettes(tgPosVignettes.getSelectedToggle().getUserData().toString());
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                afficheVignettes();
            }
        });

        slTailleVignettes.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleImageVignettes(taille);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                afficheVignettes();
            }
        });

        slOpaciteVignettes.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpaciteVignettes(opac);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                afficheVignettes();
            }
        });

        cbAfficheVignettes.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheVignettes((boolean) new_val);
                afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
                afficheVignettes();
            }
        });

        cpCouleurFondVignettes.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String coul = cpCouleurFondVignettes.getValue().toString().substring(2, 8);
            setStrCouleurFondVignettes("#" + coul);
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            afficheVignettes();
        });
        cpCouleurTexteVignettes.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String coul = cpCouleurTexteVignettes.getValue().toString().substring(2, 8);
            setStrCouleurTexteVignettes("#" + coul);
            afficheBarreClassique(getStrPositionBarreClassique(), getOffsetXBarreClassique(), getOffsetYBarreClassique(), getTailleBarreClassique(), getStyleBarreClassique(), getStrStyleHotSpots(), getEspacementBarreClassique());
            afficheVignettes();
        });

        /*
         Listeners ComboMenu
         */
        tgPosComboMenu.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosComboMenu.getSelectedToggle() != null) {
                setStrPositionXComboMenu(tgPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[1]);
                setStrPositionYComboMenu(tgPosComboMenu.getSelectedToggle().getUserData().toString().split(":")[0]);
                afficheComboMenu();
            }
        });

        cbAfficheComboMenu.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheComboMenu((boolean) new_val);
                afficheComboMenu();
            }
        });
        cbAfficheComboMenuImages.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheComboMenuImages((boolean) new_val);
                afficheComboMenu();
            }
        });

        bdfOffsetXComboMenu.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetXComboMenu(new_value.doubleValue());
            afficheComboMenu();
        });

        bdfOffsetYComboMenu.numberProperty().addListener((ov, old_value, new_value) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setOffsetYComboMenu(new_value.doubleValue());
            afficheComboMenu();
        });


        /*
         Listeners Plan
         */
        cbAffichePlan.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAffichePlan((boolean) new_val);
                tabPlan.setDisable(!isbAffichePlan());
                mniAffichagePlan.setDisable(!isbAffichePlan());
                ivAjouterPlan.setDisable(!isbAffichePlan());
                mniAjouterPlan.setDisable(!isbAffichePlan());
                if (new_val) {
                    ivAjouterPlan.setOpacity(1.0);
                } else {
                    ivAjouterPlan.setOpacity(0.3);
                }
                affichePlan();
            }
        });
        cbAfficheRadar.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (new_val != null) {
                setbAfficheRadar((boolean) new_val);
                affichePlan();
            }
        });

        tgPosPlan.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (tgPosPlan.getSelectedToggle() != null) {
                setStrPositionPlan(tgPosPlan.getSelectedToggle().getUserData().toString());
                affichePlan();
            }
        });
        slLargeurPlan.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setLargeurPlan(taille);
                lblLargeurPlan.setText(rbLocalisation.getString("interface.largeurPlan") + " (" + Math.round(taille) + "px )");
                affichePlan();
            }
        });
        slOpacitePlan.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opac = (double) newValue;
                setOpacitePlan(opac);
                affichePlan();
            }
        });

        cpCouleurFondPlan.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurFondPlan.getValue().toString().substring(2, 8);
            setCouleurFondPlan(cpCouleurFondPlan.getValue());
            setStrCouleurFondPlan(strCoul);
            affichePlan();
        });
        cpCouleurTextePlan.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurTextePlan.getValue().toString().substring(2, 8);
            setCouleurTextePlan(cpCouleurTextePlan.getValue());
            setStrCouleurTextePlan(strCoul);
            affichePlan();
        });
        slTailleRadar.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double taille = (double) newValue;
                setTailleRadar(taille);
                affichePlan();
            }
        });
        slOpaciteRadar.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            if (newValue != null) {
                double opacite = (double) newValue;
                setOpaciteRadar(opacite);
                affichePlan();
            }
        });
        cpCouleurFondRadar.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurFondRadar.getValue().toString().substring(2, 8);
            setCouleurFondRadar(cpCouleurFondRadar.getValue());
            setStrCouleurFondRadar(strCoul);
            affichePlan();
        });
        cpCouleurLigneRadar.setOnAction((e) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            String strCoul = cpCouleurLigneRadar.getValue().toString().substring(2, 8);
            setCouleurLigneRadar(cpCouleurLigneRadar.getValue());
            setStrCouleurLigneRadar(strCoul);
            affichePlan();
        });

        /*
         Listeners Menu Contextuel
         */
        cbAfficheMenuContextuel.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAfficheMenuContextuel((boolean) new_val);
        });
        cbAffichePrecSuivMC.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAffichePrecSuivMC((boolean) new_val);
        });
        cbAffichePlanetNormalMC.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAffichePlanetNormalMC((boolean) new_val);
        });
        cbAffichePersMC1.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAffichePersMC1((boolean) new_val);
            if (isbAffichePersMC1()) {
                tfPersLib1.setDisable(false);
                tfPersURL1.setDisable(false);
                cbAffichePersMC2.setDisable(false);
            } else {
                tfPersLib1.setDisable(true);
                tfPersURL1.setDisable(true);
                cbAffichePersMC2.setSelected(false);
                cbAffichePersMC2.setDisable(true);
            }
        });
        cbAffichePersMC2.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setbAffichePersMC2((boolean) new_val);
            if (isbAffichePersMC2()) {
                tfPersLib2.setDisable(false);
                tfPersURL2.setDisable(false);
            } else {
                tfPersLib2.setDisable(true);
                tfPersURL2.setDisable(true);
            }
        });
        tfPersLib1.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrPersLib1(tfPersLib1.getText());
        });
        tfPersLib2.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrPersLib2(tfPersLib2.getText());
        });
        tfPersURL1.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrPersURL1(tfPersURL1.getText());
        });
        tfPersURL2.textProperty().addListener((ov, oldValue, newValue) -> {
            if (iNombrePanoramiques != 0) {
                bDejaSauve = false;
                stPrincipal.setTitle(stPrincipal.getTitle().replace(" *", "") + " *");
            }
            setStrPersURL2(tfPersURL2.getText());
        });

    }

    /**
     * @return the offsetXBarreClassique
     */
    public double getOffsetXBarreClassique() {
        return offsetXBarreClassique;
    }

    /**
     * @param offsetXBarreClassique the offsetXBarreClassique to set
     */
    public void setOffsetXBarreClassique(double offsetXBarreClassique) {
        this.offsetXBarreClassique = offsetXBarreClassique;
    }

    /**
     * @return the offsetYBarreClassique
     */
    public double getOffsetYBarreClassique() {
        return offsetYBarreClassique;
    }

    /**
     * @param offsetYBarreClassique the offsetYBarreClassique to set
     */
    public void setOffsetYBarreClassique(double offsetYBarreClassique) {
        this.offsetYBarreClassique = offsetYBarreClassique;
    }

    /**
     * @return the tailleBarreClassique
     */
    public double getTailleBarreClassique() {
        return tailleBarreClassique;
    }

    /**
     * @param tailleBarreClassique the tailleBarreClassique to set
     */
    public void setTailleBarreClassique(double tailleBarreClassique) {
        this.tailleBarreClassique = tailleBarreClassique;
    }

    /**
     * @return the espacementBarreClassique
     */
    public double getEspacementBarreClassique() {
        return espacementBarreClassique;
    }

    /**
     * @param espacementBarreClassique the espacementBarreClassique to set
     */
    public void setEspacementBarreClassique(double espacementBarreClassique) {
        this.espacementBarreClassique = espacementBarreClassique;
    }

    /**
     * @return the strStyleDefautBarreClassique
     */
    public String getStrStyleDefautBarreClassique() {
        return strStyleDefautBarreClassique;
    }

    /**
     * @return the strPositionBarreClassique
     */
    public String getStrPositionBarreClassique() {
        return strPositionBarreClassique;
    }

    /**
     * @param strPositionBarreClassique the strPositionBarreClassique to set
     */
    public void setStrPositionBarreClassique(String strPositionBarreClassique) {
        this.strPositionBarreClassique = strPositionBarreClassique;
    }

    /**
     * @return the styleBarreClassique
     */
    public String getStyleBarreClassique() {
        return styleBarreClassique;
    }

    /**
     * @param styleBarreClassique the styleBarreClassique to set
     */
    public void setStyleBarreClassique(String styleBarreClassique) {
        this.styleBarreClassique = styleBarreClassique;
    }

    /**
     * @return the strDeplacementsBarreClassique
     */
    public String getStrDeplacementsBarreClassique() {
        return strDeplacementsBarreClassique;
    }

    /**
     * @param strDeplacementsBarreClassique the strDeplacementsBarreClassique to
     * set
     */
    public void setStrDeplacementsBarreClassique(String strDeplacementsBarreClassique) {
        this.strDeplacementsBarreClassique = strDeplacementsBarreClassique;
    }

    /**
     * @return the strZoomBarreClassique
     */
    public String getStrZoomBarreClassique() {
        return strZoomBarreClassique;
    }

    /**
     * @param strZoomBarreClassique the strZoomBarreClassique to set
     */
    public void setStrZoomBarreClassique(String strZoomBarreClassique) {
        this.strZoomBarreClassique = strZoomBarreClassique;
    }

    /**
     * @return the strOutilsBarreClassique
     */
    public String getStrOutilsBarreClassique() {
        return strOutilsBarreClassique;
    }

    /**
     * @param strOutilsBarreClassique the strOutilsBarreClassique to set
     */
    public void setStrOutilsBarreClassique(String strOutilsBarreClassique) {
        this.strOutilsBarreClassique = strOutilsBarreClassique;
    }

    /**
     * @return the strRotationBarreClassique
     */
    public String getStrRotationBarreClassique() {
        return strRotationBarreClassique;
    }

    /**
     * @param strRotationBarreClassique the strRotationBarreClassique to set
     */
    public void setStrRotationBarreClassique(String strRotationBarreClassique) {
        this.strRotationBarreClassique = strRotationBarreClassique;
    }

    /**
     * @return the strPleinEcranBarreClassique
     */
    public String getStrPleinEcranBarreClassique() {
        return strPleinEcranBarreClassique;
    }

    /**
     * @param strPleinEcranBarreClassique the strPleinEcranBarreClassique to set
     */
    public void setStrPleinEcranBarreClassique(String strPleinEcranBarreClassique) {
        this.strPleinEcranBarreClassique = strPleinEcranBarreClassique;
    }

    /**
     * @return the strSourisBarreClassique
     */
    public String getStrSourisBarreClassique() {
        return strSourisBarreClassique;
    }

    /**
     * @param strSourisBarreClassique the strSourisBarreClassique to set
     */
    public void setStrSourisBarreClassique(String strSourisBarreClassique) {
        this.strSourisBarreClassique = strSourisBarreClassique;
    }

    /**
     * @return the strVisibiliteBarreClassique
     */
    public String getStrVisibiliteBarreClassique() {
        return strVisibiliteBarreClassique;
    }

    /**
     * @param strVisibiliteBarreClassique the strVisibiliteBarreClassique to set
     */
    public void setStrVisibiliteBarreClassique(String strVisibiliteBarreClassique) {
        this.strVisibiliteBarreClassique = strVisibiliteBarreClassique;
    }

    /**
     * @return the bCouleurOrigineBarrePersonnalisee
     */
    public boolean isbCouleurOrigineBarrePersonnalisee() {
        return bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * @param bCouleurOrigineBarrePersonnalisee the
     * bCouleurOrigineBarrePersonnalisee to set
     */
    public void setbCouleurOrigineBarrePersonnalisee(boolean bCouleurOrigineBarrePersonnalisee) {
        this.bCouleurOrigineBarrePersonnalisee = bCouleurOrigineBarrePersonnalisee;
    }

    /**
     * @return the iNombreZonesBarrePersonnalisee
     */
    public int getiNombreZonesBarrePersonnalisee() {
        return iNombreZonesBarrePersonnalisee;
    }

    /**
     * @param iNombreZonesBarrePersonnalisee the iNombreZonesBarrePersonnalisee
     * to set
     */
    public void setiNombreZonesBarrePersonnalisee(int iNombreZonesBarrePersonnalisee) {
        this.iNombreZonesBarrePersonnalisee = iNombreZonesBarrePersonnalisee;
    }

    /**
     * @return the offsetXBarrePersonnalisee
     */
    public double getOffsetXBarrePersonnalisee() {
        return offsetXBarrePersonnalisee;
    }

    /**
     * @param offsetXBarrePersonnalisee the offsetXBarrePersonnalisee to set
     */
    public void setOffsetXBarrePersonnalisee(double offsetXBarrePersonnalisee) {
        this.offsetXBarrePersonnalisee = offsetXBarrePersonnalisee;
    }

    /**
     * @return the offsetYBarrePersonnalisee
     */
    public double getOffsetYBarrePersonnalisee() {
        return offsetYBarrePersonnalisee;
    }

    /**
     * @param offsetYBarrePersonnalisee the offsetYBarrePersonnalisee to set
     */
    public void setOffsetYBarrePersonnalisee(double offsetYBarrePersonnalisee) {
        this.offsetYBarrePersonnalisee = offsetYBarrePersonnalisee;
    }

    /**
     * @return the tailleBarrePersonnalisee
     */
    public double getTailleBarrePersonnalisee() {
        return tailleBarrePersonnalisee;
    }

    /**
     * @param tailleBarrePersonnalisee the tailleBarrePersonnalisee to set
     */
    public void setTailleBarrePersonnalisee(double tailleBarrePersonnalisee) {
        this.tailleBarrePersonnalisee = tailleBarrePersonnalisee;
    }

    /**
     * @return the tailleIconesBarrePersonnalisee
     */
    public double getTailleIconesBarrePersonnalisee() {
        return tailleIconesBarrePersonnalisee;
    }

    /**
     * @param tailleIconesBarrePersonnalisee the tailleIconesBarrePersonnalisee
     * to set
     */
    public void setTailleIconesBarrePersonnalisee(double tailleIconesBarrePersonnalisee) {
        this.tailleIconesBarrePersonnalisee = tailleIconesBarrePersonnalisee;
    }

    /**
     * @return the strPositionBarrePersonnalisee
     */
    public String getStrPositionBarrePersonnalisee() {
        return strPositionBarrePersonnalisee;
    }

    /**
     * @param strPositionBarrePersonnalisee the strPositionBarrePersonnalisee to
     * set
     */
    public void setStrPositionBarrePersonnalisee(String strPositionBarrePersonnalisee) {
        this.strPositionBarrePersonnalisee = strPositionBarrePersonnalisee;
    }

    /**
     * @return the strDeplacementsBarrePersonnalisee
     */
    public String getStrDeplacementsBarrePersonnalisee() {
        return strDeplacementsBarrePersonnalisee;
    }

    /**
     * @param strDeplacementsBarrePersonnalisee the
     * strDeplacementsBarrePersonnalisee to set
     */
    public void setStrDeplacementsBarrePersonnalisee(String strDeplacementsBarrePersonnalisee) {
        this.strDeplacementsBarrePersonnalisee = strDeplacementsBarrePersonnalisee;
    }

    /**
     * @return the strZoomBarrePersonnalisee
     */
    public String getStrZoomBarrePersonnalisee() {
        return strZoomBarrePersonnalisee;
    }

    /**
     * @param strZoomBarrePersonnalisee the strZoomBarrePersonnalisee to set
     */
    public void setStrZoomBarrePersonnalisee(String strZoomBarrePersonnalisee) {
        this.strZoomBarrePersonnalisee = strZoomBarrePersonnalisee;
    }

    /**
     * @return the strInfoBarrePersonnalisee
     */
    public String getStrInfoBarrePersonnalisee() {
        return strInfoBarrePersonnalisee;
    }

    /**
     * @param strInfoBarrePersonnalisee the strInfoBarrePersonnalisee to set
     */
    public void setStrInfoBarrePersonnalisee(String strInfoBarrePersonnalisee) {
        this.strInfoBarrePersonnalisee = strInfoBarrePersonnalisee;
    }

    /**
     * @return the strAideBarrePersonnalisee
     */
    public String getStrAideBarrePersonnalisee() {
        return strAideBarrePersonnalisee;
    }

    /**
     * @param strAideBarrePersonnalisee the strAideBarrePersonnalisee to set
     */
    public void setStrAideBarrePersonnalisee(String strAideBarrePersonnalisee) {
        this.strAideBarrePersonnalisee = strAideBarrePersonnalisee;
    }

    /**
     * @return the strRotationBarrePersonnalisee
     */
    public String getStrRotationBarrePersonnalisee() {
        return strRotationBarrePersonnalisee;
    }

    /**
     * @param strRotationBarrePersonnalisee the strRotationBarrePersonnalisee to
     * set
     */
    public void setStrRotationBarrePersonnalisee(String strRotationBarrePersonnalisee) {
        this.strRotationBarrePersonnalisee = strRotationBarrePersonnalisee;
    }

    /**
     * @return the strPleinEcranBarrePersonnalisee
     */
    public String getStrPleinEcranBarrePersonnalisee() {
        return strPleinEcranBarrePersonnalisee;
    }

    /**
     * @param strPleinEcranBarrePersonnalisee the
     * strPleinEcranBarrePersonnalisee to set
     */
    public void setStrPleinEcranBarrePersonnalisee(String strPleinEcranBarrePersonnalisee) {
        this.strPleinEcranBarrePersonnalisee = strPleinEcranBarrePersonnalisee;
    }

    /**
     * @return the strSourisBarrePersonnalisee
     */
    public String getStrSourisBarrePersonnalisee() {
        return strSourisBarrePersonnalisee;
    }

    /**
     * @param strSourisBarrePersonnalisee the strSourisBarrePersonnalisee to set
     */
    public void setStrSourisBarrePersonnalisee(String strSourisBarrePersonnalisee) {
        this.strSourisBarrePersonnalisee = strSourisBarrePersonnalisee;
    }

    /**
     * @return the strVisibiliteBarrePersonnalisee
     */
    public String getStrVisibiliteBarrePersonnalisee() {
        return strVisibiliteBarrePersonnalisee;
    }

    /**
     * @param strVisibiliteBarrePersonnalisee the
     * strVisibiliteBarrePersonnalisee to set
     */
    public void setStrVisibiliteBarrePersonnalisee(String strVisibiliteBarrePersonnalisee) {
        this.strVisibiliteBarrePersonnalisee = strVisibiliteBarrePersonnalisee;
    }

    /**
     * @return the strLienImageBarrePersonnalisee
     */
    public String getStrLienImageBarrePersonnalisee() {
        return strLienImageBarrePersonnalisee;
    }

    /**
     * @param strLienImageBarrePersonnalisee the strLienImageBarrePersonnalisee
     * to set
     */
    public void setStrLienImageBarrePersonnalisee(String strLienImageBarrePersonnalisee) {
        this.strLienImageBarrePersonnalisee = strLienImageBarrePersonnalisee;
    }

    /**
     * @return the strLien1BarrePersonnalisee
     */
    public String getStrLien1BarrePersonnalisee() {
        return strLien1BarrePersonnalisee;
    }

    /**
     * @param strLien1BarrePersonnalisee the strLien1BarrePersonnalisee to set
     */
    public void setStrLien1BarrePersonnalisee(String strLien1BarrePersonnalisee) {
        this.strLien1BarrePersonnalisee = strLien1BarrePersonnalisee;
    }

    /**
     * @return the strLien2BarrePersonnalisee
     */
    public String getStrLien2BarrePersonnalisee() {
        return strLien2BarrePersonnalisee;
    }

    /**
     * @param strLien2BarrePersonnalisee the strLien2BarrePersonnalisee to set
     */
    public void setStrLien2BarrePersonnalisee(String strLien2BarrePersonnalisee) {
        this.strLien2BarrePersonnalisee = strLien2BarrePersonnalisee;
    }

    /**
     * @return the wiBarrePersonnaliseeCouleur
     */
    public WritableImage getWiBarrePersonnaliseeCouleur() {
        return wiBarrePersonnaliseeCouleur;
    }

    /**
     * @param wiBarrePersonnaliseeCouleur the wiBarrePersonnaliseeCouleur to set
     */
    public void setWiBarrePersonnaliseeCouleur(WritableImage wiBarrePersonnaliseeCouleur) {
        this.wiBarrePersonnaliseeCouleur = wiBarrePersonnaliseeCouleur;
    }

    /**
     * @return the bAfficheTitre
     */
    public boolean isbAfficheTitre() {
        return bAfficheTitre;
    }

    /**
     * @param bAfficheTitre the bAfficheTitre to set
     */
    public void setbAfficheTitre(boolean bAfficheTitre) {
        this.bAfficheTitre = bAfficheTitre;
    }

    /**
     * @return the strTitrePoliceNom
     */
    public String getStrTitrePoliceNom() {
        return strTitrePoliceNom;
    }

    /**
     * @param strTitrePoliceNom the strTitrePoliceNom to set
     */
    public void setStrTitrePoliceNom(String strTitrePoliceNom) {
        this.strTitrePoliceNom = strTitrePoliceNom;
    }

    /**
     * @return the strTitrePoliceStyle
     */
    public String getStrTitrePoliceStyle() {
        return strTitrePoliceStyle;
    }

    /**
     * @param strTitrePoliceStyle the strTitrePoliceStyle to set
     */
    public void setStrTitrePoliceStyle(String strTitrePoliceStyle) {
        this.strTitrePoliceStyle = strTitrePoliceStyle;
    }

    /**
     * @return the strTitrePoliceTaille
     */
    public String getStrTitrePoliceTaille() {
        return strTitrePoliceTaille;
    }

    /**
     * @param strTitrePoliceTaille the strTitrePoliceTaille to set
     */
    public void setStrTitrePoliceTaille(String strTitrePoliceTaille) {
        this.strTitrePoliceTaille = strTitrePoliceTaille;
    }

    /**
     * @return the strCouleurTitre
     */
    public String getStrCouleurTitre() {
        return strCouleurTitre;
    }

    /**
     * @param strCouleurTitre the strCouleurTitre to set
     */
    public void setStrCouleurTitre(String strCouleurTitre) {
        this.strCouleurTitre = strCouleurTitre;
    }

    /**
     * @return the strCouleurFondTitre
     */
    public String getStrCouleurFondTitre() {
        return strCouleurFondTitre;
    }

    /**
     * @param strCouleurFondTitre the strCouleurFondTitre to set
     */
    public void setStrCouleurFondTitre(String strCouleurFondTitre) {
        this.strCouleurFondTitre = strCouleurFondTitre;
    }

    /**
     * @return the titreOpacite
     */
    public double getTitreOpacite() {
        return titreOpacite;
    }

    /**
     * @param titreOpacite the titreOpacite to set
     */
    public void setTitreOpacite(double titreOpacite) {
        this.titreOpacite = titreOpacite;
    }

    /**
     * @return the titreTaille
     */
    public double getTitreTaille() {
        return titreTaille;
    }

    /**
     * @param titreTaille the titreTaille to set
     */
    public void setTitreTaille(double titreTaille) {
        this.titreTaille = titreTaille;
    }

    /**
     * @return the strCouleurDiaporama
     */
    public String getStrCouleurDiaporama() {
        return strCouleurDiaporama;
    }

    /**
     * @param strCouleurDiaporama the strCouleurDiaporama to set
     */
    public void setStrCouleurDiaporama(String strCouleurDiaporama) {
        this.strCouleurDiaporama = strCouleurDiaporama;
    }

    /**
     * @return the diaporamaOpacite
     */
    public double getDiaporamaOpacite() {
        return diaporamaOpacite;
    }

    /**
     * @param diaporamaOpacite the diaporamaOpacite to set
     */
    public void setDiaporamaOpacite(double diaporamaOpacite) {
        this.diaporamaOpacite = diaporamaOpacite;
    }

    /**
     * @return the bAfficheBoussole
     */
    public boolean isbAfficheBoussole() {
        return bAfficheBoussole;
    }

    /**
     * @param bAfficheBoussole the bAfficheBoussole to set
     */
    public void setbAfficheBoussole(boolean bAfficheBoussole) {
        this.bAfficheBoussole = bAfficheBoussole;
    }

    /**
     * @return the strImageBoussole
     */
    public String getStrImageBoussole() {
        return strImageBoussole;
    }

    /**
     * @param strImageBoussole the strImageBoussole to set
     */
    public void setStrImageBoussole(String strImageBoussole) {
        this.strImageBoussole = strImageBoussole;
    }

    /**
     * @return the strPositionBoussole
     */
    public String getStrPositionBoussole() {
        return strPositionBoussole;
    }

    /**
     * @param strPositionBoussole the strPositionBoussole to set
     */
    public void setStrPositionBoussole(String strPositionBoussole) {
        this.strPositionBoussole = strPositionBoussole;
    }

    /**
     * @return the offsetXBoussole
     */
    public double getOffsetXBoussole() {
        return offsetXBoussole;
    }

    /**
     * @param offsetXBoussole the offsetXBoussole to set
     */
    public void setOffsetXBoussole(double offsetXBoussole) {
        this.offsetXBoussole = offsetXBoussole;
    }

    /**
     * @return the offsetYBoussole
     */
    public double getOffsetYBoussole() {
        return offsetYBoussole;
    }

    /**
     * @param offsetYBoussole the offsetYBoussole to set
     */
    public void setOffsetYBoussole(double offsetYBoussole) {
        this.offsetYBoussole = offsetYBoussole;
    }

    /**
     * @return the tailleBoussole
     */
    public double getTailleBoussole() {
        return tailleBoussole;
    }

    /**
     * @param tailleBoussole the tailleBoussole to set
     */
    public void setTailleBoussole(double tailleBoussole) {
        this.tailleBoussole = tailleBoussole;
    }

    /**
     * @return the opaciteBoussole
     */
    public double getOpaciteBoussole() {
        return opaciteBoussole;
    }

    /**
     * @param opaciteBoussole the opaciteBoussole to set
     */
    public void setOpaciteBoussole(double opaciteBoussole) {
        this.opaciteBoussole = opaciteBoussole;
    }

    /**
     * @return the bAiguilleMobileBoussole
     */
    public boolean isbAiguilleMobileBoussole() {
        return bAiguilleMobileBoussole;
    }

    /**
     * @param bAiguilleMobileBoussole the bAiguilleMobileBoussole to set
     */
    public void setbAiguilleMobileBoussole(boolean bAiguilleMobileBoussole) {
        this.bAiguilleMobileBoussole = bAiguilleMobileBoussole;
    }

    /**
     * @return the bFenetreInfoPersonnalise
     */
    public boolean isbFenetreInfoPersonnalise() {
        return bFenetreInfoPersonnalise;
    }

    /**
     * @param bFenetreInfoPersonnalise the bFenetreInfoPersonnalise to set
     */
    public void setbFenetreInfoPersonnalise(boolean bFenetreInfoPersonnalise) {
        this.bFenetreInfoPersonnalise = bFenetreInfoPersonnalise;
    }

    /**
     * @return the bFenetreAidePersonnalise
     */
    public boolean isbFenetreAidePersonnalise() {
        return bFenetreAidePersonnalise;
    }

    /**
     * @param bFenetreAidePersonnalise the bFenetreAidePersonnalise to set
     */
    public void setbFenetreAidePersonnalise(boolean bFenetreAidePersonnalise) {
        this.bFenetreAidePersonnalise = bFenetreAidePersonnalise;
    }

    /**
     * @return the fenetreInfoTaille
     */
    public double getFenetreInfoTaille() {
        return fenetreInfoTaille;
    }

    /**
     * @param fenetreInfoTaille the fenetreInfoTaille to set
     */
    public void setFenetreInfoTaille(double fenetreInfoTaille) {
        this.fenetreInfoTaille = fenetreInfoTaille;
    }

    /**
     * @return the fenetreAideTaille
     */
    public double getFenetreAideTaille() {
        return fenetreAideTaille;
    }

    /**
     * @param fenetreAideTaille the fenetreAideTaille to set
     */
    public void setFenetreAideTaille(double fenetreAideTaille) {
        this.fenetreAideTaille = fenetreAideTaille;
    }

    /**
     * @return the fenetreInfoPosX
     */
    public double getFenetreInfoPosX() {
        return fenetreInfoPosX;
    }

    /**
     * @param fenetreInfoPosX the fenetreInfoPosX to set
     */
    public void setFenetreInfoPosX(double fenetreInfoPosX) {
        this.fenetreInfoPosX = fenetreInfoPosX;
    }

    /**
     * @return the fenetreInfoPosY
     */
    public double getFenetreInfoPosY() {
        return fenetreInfoPosY;
    }

    /**
     * @param fenetreInfoPosY the fenetreInfoPosY to set
     */
    public void setFenetreInfoPosY(double fenetreInfoPosY) {
        this.fenetreInfoPosY = fenetreInfoPosY;
    }

    /**
     * @return the fenetreAidePosX
     */
    public double getFenetreAidePosX() {
        return fenetreAidePosX;
    }

    /**
     * @param fenetreAidePosX the fenetreAidePosX to set
     */
    public void setFenetreAidePosX(double fenetreAidePosX) {
        this.fenetreAidePosX = fenetreAidePosX;
    }

    /**
     * @return the fenetreAidePosY
     */
    public double getFenetreAidePosY() {
        return fenetreAidePosY;
    }

    /**
     * @param fenetreAidePosY the fenetreAidePosY to set
     */
    public void setFenetreAidePosY(double fenetreAidePosY) {
        this.fenetreAidePosY = fenetreAidePosY;
    }

    /**
     * @return the fenetreInfoposX
     */
    public double getFenetreInfoposX() {
        return fenetreInfoposX;
    }

    /**
     * @param fenetreInfoposX the fenetreInfoposX to set
     */
    public void setFenetreInfoposX(double fenetreInfoposX) {
        this.fenetreInfoposX = fenetreInfoposX;
    }

    /**
     * @return the fenetreInfoOpacite
     */
    public double getFenetreInfoOpacite() {
        return fenetreInfoOpacite;
    }

    /**
     * @param fenetreInfoOpacite the fenetreInfoOpacite to set
     */
    public void setFenetreInfoOpacite(double fenetreInfoOpacite) {
        this.fenetreInfoOpacite = fenetreInfoOpacite;
    }

    /**
     * @return the fenetreAideOpacite
     */
    public double getFenetreAideOpacite() {
        return fenetreAideOpacite;
    }

    /**
     * @param fenetreAideOpacite the fenetreAideOpacite to set
     */
    public void setFenetreAideOpacite(double fenetreAideOpacite) {
        this.fenetreAideOpacite = fenetreAideOpacite;
    }

    /**
     * @return the fenetrePoliceTaille
     */
    public double getFenetrePoliceTaille() {
        return fenetrePoliceTaille;
    }

    /**
     * @param fenetrePoliceTaille the fenetrePoliceTaille to set
     */
    public void setFenetrePoliceTaille(double fenetrePoliceTaille) {
        this.fenetrePoliceTaille = fenetrePoliceTaille;
    }

    /**
     * @return the fenetreURLPosX
     */
    public double getFenetreURLPosX() {
        return fenetreURLPosX;
    }

    /**
     * @param fenetreURLPosX the fenetreURLPosX to set
     */
    public void setFenetreURLPosX(double fenetreURLPosX) {
        this.fenetreURLPosX = fenetreURLPosX;
    }

    /**
     * @return the fenetreURLPosY
     */
    public double getFenetreURLPosY() {
        return fenetreURLPosY;
    }

    /**
     * @param fenetreURLPosY the fenetreURLPosY to set
     */
    public void setFenetreURLPosY(double fenetreURLPosY) {
        this.fenetreURLPosY = fenetreURLPosY;
    }

    /**
     * @return the fenetreOpaciteFond
     */
    public double getFenetreOpaciteFond() {
        return fenetreOpaciteFond;
    }

    /**
     * @param fenetreOpaciteFond the fenetreOpaciteFond to set
     */
    public void setFenetreOpaciteFond(double fenetreOpaciteFond) {
        this.fenetreOpaciteFond = fenetreOpaciteFond;
    }

    /**
     * @return the strFenetreInfoImage
     */
    public String getStrFenetreInfoImage() {
        return strFenetreInfoImage;
    }

    /**
     * @param strFenetreInfoImage the strFenetreInfoImage to set
     */
    public void setStrFenetreInfoImage(String strFenetreInfoImage) {
        this.strFenetreInfoImage = strFenetreInfoImage;
    }

    /**
     * @return the strFenetreAideImage
     */
    public String getStrFenetreAideImage() {
        return strFenetreAideImage;
    }

    /**
     * @param strFenetreAideImage the strFenetreAideImage to set
     */
    public void setStrFenetreAideImage(String strFenetreAideImage) {
        this.strFenetreAideImage = strFenetreAideImage;
    }

    /**
     * @return the strFenetreURL
     */
    public String getStrFenetreURL() {
        return strFenetreURL;
    }

    /**
     * @param strFenetreURL the strFenetreURL to set
     */
    public void setStrFenetreURL(String strFenetreURL) {
        this.strFenetreURL = strFenetreURL;
    }

    /**
     * @return the strFenetreTexteURL
     */
    public String getStrFenetreTexteURL() {
        return strFenetreTexteURL;
    }

    /**
     * @param strFenetreTexteURL the strFenetreTexteURL to set
     */
    public void setStrFenetreTexteURL(String strFenetreTexteURL) {
        this.strFenetreTexteURL = strFenetreTexteURL;
    }

    /**
     * @return the strFenetreURLInfobulle
     */
    public String getStrFenetreURLInfobulle() {
        return strFenetreURLInfobulle;
    }

    /**
     * @param strFenetreURLInfobulle the strFenetreURLInfobulle to set
     */
    public void setStrFenetreURLInfobulle(String strFenetreURLInfobulle) {
        this.strFenetreURLInfobulle = strFenetreURLInfobulle;
    }

    /**
     * @return the strFenetreURLCouleur
     */
    public String getStrFenetreURLCouleur() {
        return strFenetreURLCouleur;
    }

    /**
     * @param strFenetreURLCouleur the strFenetreURLCouleur to set
     */
    public void setStrFenetreURLCouleur(String strFenetreURLCouleur) {
        this.strFenetreURLCouleur = strFenetreURLCouleur;
    }

    /**
     * @return the strFenetrePolice
     */
    public String getStrFenetrePolice() {
        return strFenetrePolice;
    }

    /**
     * @param strFenetrePolice the strFenetrePolice to set
     */
    public void setStrFenetrePolice(String strFenetrePolice) {
        this.strFenetrePolice = strFenetrePolice;
    }

    /**
     * @return the strFenetreCouleurFond
     */
    public String getStrFenetreCouleurFond() {
        return strFenetreCouleurFond;
    }

    /**
     * @param strFenetreCouleurFond the strFenetreCouleurFond to set
     */
    public void setStrFenetreCouleurFond(String strFenetreCouleurFond) {
        this.strFenetreCouleurFond = strFenetreCouleurFond;
    }

    /**
     * @return the bAfficheMasque
     */
    public boolean isbAfficheMasque() {
        return bAfficheMasque;
    }

    /**
     * @param bAfficheMasque the bAfficheMasque to set
     */
    public void setbAfficheMasque(boolean bAfficheMasque) {
        this.bAfficheMasque = bAfficheMasque;
    }

    /**
     * @return the strImageMasque
     */
    public String getStrImageMasque() {
        return strImageMasque;
    }

    /**
     * @param strImageMasque the strImageMasque to set
     */
    public void setStrImageMasque(String strImageMasque) {
        this.strImageMasque = strImageMasque;
    }

    /**
     * @return the strPositionMasque
     */
    public String getStrPositionMasque() {
        return strPositionMasque;
    }

    /**
     * @param strPositionMasque the strPositionMasque to set
     */
    public void setStrPositionMasque(String strPositionMasque) {
        this.strPositionMasque = strPositionMasque;
    }

    /**
     * @return the dXMasque
     */
    public double getdXMasque() {
        return dXMasque;
    }

    /**
     * @param dXMasque the dXMasque to set
     */
    public void setdXMasque(double dXMasque) {
        this.dXMasque = dXMasque;
    }

    /**
     * @return the dYMasque
     */
    public double getdYMasque() {
        return dYMasque;
    }

    /**
     * @param dYMasque the dYMasque to set
     */
    public void setdYMasque(double dYMasque) {
        this.dYMasque = dYMasque;
    }

    /**
     * @return the tailleMasque
     */
    public double getTailleMasque() {
        return tailleMasque;
    }

    /**
     * @param tailleMasque the tailleMasque to set
     */
    public void setTailleMasque(double tailleMasque) {
        this.tailleMasque = tailleMasque;
    }

    /**
     * @return the opaciteMasque
     */
    public double getOpaciteMasque() {
        return opaciteMasque;
    }

    /**
     * @param opaciteMasque the opaciteMasque to set
     */
    public void setOpaciteMasque(double opaciteMasque) {
        this.opaciteMasque = opaciteMasque;
    }

    /**
     * @return the bMasqueNavigation
     */
    public boolean isbMasqueNavigation() {
        return bMasqueNavigation;
    }

    /**
     * @param bMasqueNavigation the bMasqueNavigation to set
     */
    public void setbMasqueNavigation(boolean bMasqueNavigation) {
        this.bMasqueNavigation = bMasqueNavigation;
    }

    /**
     * @return the bMasqueBoussole
     */
    public boolean isbMasqueBoussole() {
        return bMasqueBoussole;
    }

    /**
     * @param bMasqueBoussole the bMasqueBoussole to set
     */
    public void setbMasqueBoussole(boolean bMasqueBoussole) {
        this.bMasqueBoussole = bMasqueBoussole;
    }

    /**
     * @return the bMasqueTitre
     */
    public boolean isbMasqueTitre() {
        return bMasqueTitre;
    }

    /**
     * @param bMasqueTitre the bMasqueTitre to set
     */
    public void setbMasqueTitre(boolean bMasqueTitre) {
        this.bMasqueTitre = bMasqueTitre;
    }

    /**
     * @return the bMasquePlan
     */
    public boolean isbMasquePlan() {
        return bMasquePlan;
    }

    /**
     * @param bMasquePlan the bMasquePlan to set
     */
    public void setbMasquePlan(boolean bMasquePlan) {
        this.bMasquePlan = bMasquePlan;
    }

    /**
     * @return the bMasqueReseaux
     */
    public boolean isbMasqueReseaux() {
        return bMasqueReseaux;
    }

    /**
     * @param bMasqueReseaux the bMasqueReseaux to set
     */
    public void setbMasqueReseaux(boolean bMasqueReseaux) {
        this.bMasqueReseaux = bMasqueReseaux;
    }

    /**
     * @return the bMasqueVignettes
     */
    public boolean isbMasqueVignettes() {
        return bMasqueVignettes;
    }

    /**
     * @param bMasqueVignettes the bMasqueVignettes to set
     */
    public void setbMasqueVignettes(boolean bMasqueVignettes) {
        this.bMasqueVignettes = bMasqueVignettes;
    }

    /**
     * @return the bMasqueCombo
     */
    public boolean isbMasqueCombo() {
        return bMasqueCombo;
    }

    /**
     * @param bMasqueCombo the bMasqueCombo to set
     */
    public void setbMasqueCombo(boolean bMasqueCombo) {
        this.bMasqueCombo = bMasqueCombo;
    }

    /**
     * @return the bMasqueSuivPrec
     */
    public boolean isbMasqueSuivPrec() {
        return bMasqueSuivPrec;
    }

    /**
     * @param bMasqueSuivPrec the bMasqueSuivPrec to set
     */
    public void setbMasqueSuivPrec(boolean bMasqueSuivPrec) {
        this.bMasqueSuivPrec = bMasqueSuivPrec;
    }

    /**
     * @return the bMasqueHotspots
     */
    public boolean isbMasqueHotspots() {
        return bMasqueHotspots;
    }

    /**
     * @param bMasqueHotspots the bMasqueHotspots to set
     */
    public void setbMasqueHotspots(boolean bMasqueHotspots) {
        this.bMasqueHotspots = bMasqueHotspots;
    }

    /**
     * @return the bAfficheReseauxSociaux
     */
    public boolean isbAfficheReseauxSociaux() {
        return bAfficheReseauxSociaux;
    }

    /**
     * @param bAfficheReseauxSociaux the bAfficheReseauxSociaux to set
     */
    public void setbAfficheReseauxSociaux(boolean bAfficheReseauxSociaux) {
        this.bAfficheReseauxSociaux = bAfficheReseauxSociaux;
    }

    /**
     * @return the strImageReseauxSociauxTwitter
     */
    public String getStrImageReseauxSociauxTwitter() {
        return strImageReseauxSociauxTwitter;
    }

    /**
     * @param strImageReseauxSociauxTwitter the strImageReseauxSociauxTwitter to
     * set
     */
    public void setStrImageReseauxSociauxTwitter(String strImageReseauxSociauxTwitter) {
        this.strImageReseauxSociauxTwitter = strImageReseauxSociauxTwitter;
    }

    /**
     * @return the strImageReseauxSociauxGoogle
     */
    public String getStrImageReseauxSociauxGoogle() {
        return strImageReseauxSociauxGoogle;
    }

    /**
     * @param strImageReseauxSociauxGoogle the strImageReseauxSociauxGoogle to
     * set
     */
    public void setStrImageReseauxSociauxGoogle(String strImageReseauxSociauxGoogle) {
        this.strImageReseauxSociauxGoogle = strImageReseauxSociauxGoogle;
    }

    /**
     * @return the strImageReseauxSociauxFacebook
     */
    public String getStrImageReseauxSociauxFacebook() {
        return strImageReseauxSociauxFacebook;
    }

    /**
     * @param strImageReseauxSociauxFacebook the strImageReseauxSociauxFacebook
     * to set
     */
    public void setStrImageReseauxSociauxFacebook(String strImageReseauxSociauxFacebook) {
        this.strImageReseauxSociauxFacebook = strImageReseauxSociauxFacebook;
    }

    /**
     * @return the strImageReseauxSociauxEmail
     */
    public String getStrImageReseauxSociauxEmail() {
        return strImageReseauxSociauxEmail;
    }

    /**
     * @param strImageReseauxSociauxEmail the strImageReseauxSociauxEmail to set
     */
    public void setStrImageReseauxSociauxEmail(String strImageReseauxSociauxEmail) {
        this.strImageReseauxSociauxEmail = strImageReseauxSociauxEmail;
    }

    /**
     * @return the strPositionReseauxSociaux
     */
    public String getStrPositionReseauxSociaux() {
        return strPositionReseauxSociaux;
    }

    /**
     * @param strPositionReseauxSociaux the strPositionReseauxSociaux to set
     */
    public void setStrPositionReseauxSociaux(String strPositionReseauxSociaux) {
        this.strPositionReseauxSociaux = strPositionReseauxSociaux;
    }

    /**
     * @return the dXReseauxSociaux
     */
    public double getdXReseauxSociaux() {
        return dXReseauxSociaux;
    }

    /**
     * @param dXReseauxSociaux the dXReseauxSociaux to set
     */
    public void setdXReseauxSociaux(double dXReseauxSociaux) {
        this.dXReseauxSociaux = dXReseauxSociaux;
    }

    /**
     * @return the dYReseauxSociaux
     */
    public double getdYReseauxSociaux() {
        return dYReseauxSociaux;
    }

    /**
     * @param dYReseauxSociaux the dYReseauxSociaux to set
     */
    public void setdYReseauxSociaux(double dYReseauxSociaux) {
        this.dYReseauxSociaux = dYReseauxSociaux;
    }

    /**
     * @return the tailleReseauxSociaux
     */
    public double getTailleReseauxSociaux() {
        return tailleReseauxSociaux;
    }

    /**
     * @param tailleReseauxSociaux the tailleReseauxSociaux to set
     */
    public void setTailleReseauxSociaux(double tailleReseauxSociaux) {
        this.tailleReseauxSociaux = tailleReseauxSociaux;
    }

    /**
     * @return the opaciteReseauxSociaux
     */
    public double getOpaciteReseauxSociaux() {
        return opaciteReseauxSociaux;
    }

    /**
     * @param opaciteReseauxSociaux the opaciteReseauxSociaux to set
     */
    public void setOpaciteReseauxSociaux(double opaciteReseauxSociaux) {
        this.opaciteReseauxSociaux = opaciteReseauxSociaux;
    }

    /**
     * @return the bReseauxSociauxTwitter
     */
    public boolean isbReseauxSociauxTwitter() {
        return bReseauxSociauxTwitter;
    }

    /**
     * @param bReseauxSociauxTwitter the bReseauxSociauxTwitter to set
     */
    public void setbReseauxSociauxTwitter(boolean bReseauxSociauxTwitter) {
        this.bReseauxSociauxTwitter = bReseauxSociauxTwitter;
    }

    /**
     * @return the bReseauxSociauxGoogle
     */
    public boolean isbReseauxSociauxGoogle() {
        return bReseauxSociauxGoogle;
    }

    /**
     * @param bReseauxSociauxGoogle the bReseauxSociauxGoogle to set
     */
    public void setbReseauxSociauxGoogle(boolean bReseauxSociauxGoogle) {
        this.bReseauxSociauxGoogle = bReseauxSociauxGoogle;
    }

    /**
     * @return the bReseauxSociauxFacebook
     */
    public boolean isbReseauxSociauxFacebook() {
        return bReseauxSociauxFacebook;
    }

    /**
     * @param bReseauxSociauxFacebook the bReseauxSociauxFacebook to set
     */
    public void setbReseauxSociauxFacebook(boolean bReseauxSociauxFacebook) {
        this.bReseauxSociauxFacebook = bReseauxSociauxFacebook;
    }

    /**
     * @return the bReseauxSociauxEmail
     */
    public boolean isbReseauxSociauxEmail() {
        return bReseauxSociauxEmail;
    }

    /**
     * @param bReseauxSociauxEmail the bReseauxSociauxEmail to set
     */
    public void setbReseauxSociauxEmail(boolean bReseauxSociauxEmail) {
        this.bReseauxSociauxEmail = bReseauxSociauxEmail;
    }

    /**
     * @return the bAfficheVignettes
     */
    public boolean isbAfficheVignettes() {
        return bAfficheVignettes;
    }

    /**
     * @param bAfficheVignettes the bAfficheVignettes to set
     */
    public void setbAfficheVignettes(boolean bAfficheVignettes) {
        this.bAfficheVignettes = bAfficheVignettes;
    }

    /**
     * @return the strCouleurFondVignettes
     */
    public String getStrCouleurFondVignettes() {
        return strCouleurFondVignettes;
    }

    /**
     * @param strCouleurFondVignettes the strCouleurFondVignettes to set
     */
    public void setStrCouleurFondVignettes(String strCouleurFondVignettes) {
        this.strCouleurFondVignettes = strCouleurFondVignettes;
    }

    /**
     * @return the strCouleurTexteVignettes
     */
    public String getStrCouleurTexteVignettes() {
        return strCouleurTexteVignettes;
    }

    /**
     * @param strCouleurTexteVignettes the strCouleurTexteVignettes to set
     */
    public void setStrCouleurTexteVignettes(String strCouleurTexteVignettes) {
        this.strCouleurTexteVignettes = strCouleurTexteVignettes;
    }

    /**
     * @return the strPositionVignettes
     */
    public String getStrPositionVignettes() {
        return strPositionVignettes;
    }

    /**
     * @param strPositionVignettes the strPositionVignettes to set
     */
    public void setStrPositionVignettes(String strPositionVignettes) {
        this.strPositionVignettes = strPositionVignettes;
    }

    /**
     * @return the tailleImageVignettes
     */
    public double getTailleImageVignettes() {
        return tailleImageVignettes;
    }

    /**
     * @param tailleImageVignettes the tailleImageVignettes to set
     */
    public void setTailleImageVignettes(double tailleImageVignettes) {
        this.tailleImageVignettes = tailleImageVignettes;
    }

    /**
     * @return the opaciteVignettes
     */
    public double getOpaciteVignettes() {
        return opaciteVignettes;
    }

    /**
     * @param opaciteVignettes the opaciteVignettes to set
     */
    public void setOpaciteVignettes(double opaciteVignettes) {
        this.opaciteVignettes = opaciteVignettes;
    }

    /**
     * @return the bAfficheComboMenu
     */
    public boolean isbAfficheComboMenu() {
        return bAfficheComboMenu;
    }

    /**
     * @param bAfficheComboMenu the bAfficheComboMenu to set
     */
    public void setbAfficheComboMenu(boolean bAfficheComboMenu) {
        this.bAfficheComboMenu = bAfficheComboMenu;
    }

    /**
     * @return the bAfficheComboMenuImages
     */
    public boolean isbAfficheComboMenuImages() {
        return bAfficheComboMenuImages;
    }

    /**
     * @param bAfficheComboMenuImages the bAfficheComboMenuImages to set
     */
    public void setbAfficheComboMenuImages(boolean bAfficheComboMenuImages) {
        this.bAfficheComboMenuImages = bAfficheComboMenuImages;
    }

    /**
     * @return the strPositionXComboMenu
     */
    public String getStrPositionXComboMenu() {
        return strPositionXComboMenu;
    }

    /**
     * @param strPositionXComboMenu the strPositionXComboMenu to set
     */
    public void setStrPositionXComboMenu(String strPositionXComboMenu) {
        this.strPositionXComboMenu = strPositionXComboMenu;
    }

    /**
     * @return the strPositionYComboMenu
     */
    public String getStrPositionYComboMenu() {
        return strPositionYComboMenu;
    }

    /**
     * @param strPositionYComboMenu the strPositionYComboMenu to set
     */
    public void setStrPositionYComboMenu(String strPositionYComboMenu) {
        this.strPositionYComboMenu = strPositionYComboMenu;
    }

    /**
     * @return the offsetXComboMenu
     */
    public double getOffsetXComboMenu() {
        return offsetXComboMenu;
    }

    /**
     * @param offsetXComboMenu the offsetXComboMenu to set
     */
    public void setOffsetXComboMenu(double offsetXComboMenu) {
        this.offsetXComboMenu = offsetXComboMenu;
    }

    /**
     * @return the offsetYComboMenu
     */
    public double getOffsetYComboMenu() {
        return offsetYComboMenu;
    }

    /**
     * @param offsetYComboMenu the offsetYComboMenu to set
     */
    public void setOffsetYComboMenu(double offsetYComboMenu) {
        this.offsetYComboMenu = offsetYComboMenu;
    }

    /**
     * @return the bAffichePlan
     */
    public boolean isbAffichePlan() {
        return bAffichePlan;
    }

    /**
     * @param bAffichePlan the bAffichePlan to set
     */
    public void setbAffichePlan(boolean bAffichePlan) {
        this.bAffichePlan = bAffichePlan;
    }

    /**
     * @return the strPositionPlan
     */
    public String getStrPositionPlan() {
        return strPositionPlan;
    }

    /**
     * @param strPositionPlan the strPositionPlan to set
     */
    public void setStrPositionPlan(String strPositionPlan) {
        this.strPositionPlan = strPositionPlan;
    }

    /**
     * @return the largeurPlan
     */
    public double getLargeurPlan() {
        return largeurPlan;
    }

    /**
     * @param largeurPlan the largeurPlan to set
     */
    public void setLargeurPlan(double largeurPlan) {
        this.largeurPlan = largeurPlan;
    }

    /**
     * @return the couleurFondPlan
     */
    public Color getCouleurFondPlan() {
        return couleurFondPlan;
    }

    /**
     * @param couleurFondPlan the couleurFondPlan to set
     */
    public void setCouleurFondPlan(Color couleurFondPlan) {
        this.couleurFondPlan = couleurFondPlan;
    }

    /**
     * @return the strCouleurFondPlan
     */
    public String getStrCouleurFondPlan() {
        return strCouleurFondPlan;
    }

    /**
     * @param strCouleurFondPlan the strCouleurFondPlan to set
     */
    public void setStrCouleurFondPlan(String strCouleurFondPlan) {
        this.strCouleurFondPlan = strCouleurFondPlan;
    }

    /**
     * @return the opacitePlan
     */
    public double getOpacitePlan() {
        return opacitePlan;
    }

    /**
     * @param opacitePlan the opacitePlan to set
     */
    public void setOpacitePlan(double opacitePlan) {
        this.opacitePlan = opacitePlan;
    }

    /**
     * @return the couleurTextePlan
     */
    public Color getCouleurTextePlan() {
        return couleurTextePlan;
    }

    /**
     * @param couleurTextePlan the couleurTextePlan to set
     */
    public void setCouleurTextePlan(Color couleurTextePlan) {
        this.couleurTextePlan = couleurTextePlan;
    }

    /**
     * @return the strCouleurTextePlan
     */
    public String getStrCouleurTextePlan() {
        return strCouleurTextePlan;
    }

    /**
     * @param strCouleurTextePlan the strCouleurTextePlan to set
     */
    public void setStrCouleurTextePlan(String strCouleurTextePlan) {
        this.strCouleurTextePlan = strCouleurTextePlan;
    }

    /**
     * @return the bAfficheRadar
     */
    public boolean isbAfficheRadar() {
        return bAfficheRadar;
    }

    /**
     * @param bAfficheRadar the bAfficheRadar to set
     */
    public void setbAfficheRadar(boolean bAfficheRadar) {
        this.bAfficheRadar = bAfficheRadar;
    }

    /**
     * @return the couleurLigneRadar
     */
    public Color getCouleurLigneRadar() {
        return couleurLigneRadar;
    }

    /**
     * @param couleurLigneRadar the couleurLigneRadar to set
     */
    public void setCouleurLigneRadar(Color couleurLigneRadar) {
        this.couleurLigneRadar = couleurLigneRadar;
    }

    /**
     * @return the strCouleurLigneRadar
     */
    public String getStrCouleurLigneRadar() {
        return strCouleurLigneRadar;
    }

    /**
     * @param strCouleurLigneRadar the strCouleurLigneRadar to set
     */
    public void setStrCouleurLigneRadar(String strCouleurLigneRadar) {
        this.strCouleurLigneRadar = strCouleurLigneRadar;
    }

    /**
     * @return the couleurFondRadar
     */
    public Color getCouleurFondRadar() {
        return couleurFondRadar;
    }

    /**
     * @param couleurFondRadar the couleurFondRadar to set
     */
    public void setCouleurFondRadar(Color couleurFondRadar) {
        this.couleurFondRadar = couleurFondRadar;
    }

    /**
     * @return the strCouleurFondRadar
     */
    public String getStrCouleurFondRadar() {
        return strCouleurFondRadar;
    }

    /**
     * @param strCouleurFondRadar the strCouleurFondRadar to set
     */
    public void setStrCouleurFondRadar(String strCouleurFondRadar) {
        this.strCouleurFondRadar = strCouleurFondRadar;
    }

    /**
     * @return the tailleRadar
     */
    public double getTailleRadar() {
        return tailleRadar;
    }

    /**
     * @param tailleRadar the tailleRadar to set
     */
    public void setTailleRadar(double tailleRadar) {
        this.tailleRadar = tailleRadar;
    }

    /**
     * @return the opaciteRadar
     */
    public double getOpaciteRadar() {
        return opaciteRadar;
    }

    /**
     * @param opaciteRadar the opaciteRadar to set
     */
    public void setOpaciteRadar(double opaciteRadar) {
        this.opaciteRadar = opaciteRadar;
    }

    /**
     * @return the bAfficheMenuContextuel
     */
    public boolean isbAfficheMenuContextuel() {
        return bAfficheMenuContextuel;
    }

    /**
     * @param bAfficheMenuContextuel the bAfficheMenuContextuel to set
     */
    public void setbAfficheMenuContextuel(boolean bAfficheMenuContextuel) {
        this.bAfficheMenuContextuel = bAfficheMenuContextuel;
    }

    /**
     * @return the bAffichePrecSuivMC
     */
    public boolean isbAffichePrecSuivMC() {
        return bAffichePrecSuivMC;
    }

    /**
     * @param bAffichePrecSuivMC the bAffichePrecSuivMC to set
     */
    public void setbAffichePrecSuivMC(boolean bAffichePrecSuivMC) {
        this.bAffichePrecSuivMC = bAffichePrecSuivMC;
    }

    /**
     * @return the bAffichePlanetNormalMC
     */
    public boolean isbAffichePlanetNormalMC() {
        return bAffichePlanetNormalMC;
    }

    /**
     * @param bAffichePlanetNormalMC the bAffichePlanetNormalMC to set
     */
    public void setbAffichePlanetNormalMC(boolean bAffichePlanetNormalMC) {
        this.bAffichePlanetNormalMC = bAffichePlanetNormalMC;
    }

    /**
     * @return the bAffichePersMC1
     */
    public boolean isbAffichePersMC1() {
        return bAffichePersMC1;
    }

    /**
     * @param bAffichePersMC1 the bAffichePersMC1 to set
     */
    public void setbAffichePersMC1(boolean bAffichePersMC1) {
        this.bAffichePersMC1 = bAffichePersMC1;
    }

    /**
     * @return the strPersLib1
     */
    public String getStrPersLib1() {
        return strPersLib1;
    }

    /**
     * @param strPersLib1 the strPersLib1 to set
     */
    public void setStrPersLib1(String strPersLib1) {
        this.strPersLib1 = strPersLib1;
    }

    /**
     * @return the strPersURL1
     */
    public String getStrPersURL1() {
        return strPersURL1;
    }

    /**
     * @param strPersURL1 the strPersURL1 to set
     */
    public void setStrPersURL1(String strPersURL1) {
        this.strPersURL1 = strPersURL1;
    }

    /**
     * @return the bAffichePersMC2
     */
    public boolean isbAffichePersMC2() {
        return bAffichePersMC2;
    }

    /**
     * @param bAffichePersMC2 the bAffichePersMC2 to set
     */
    public void setbAffichePersMC2(boolean bAffichePersMC2) {
        this.bAffichePersMC2 = bAffichePersMC2;
    }

    /**
     * @return the strPersLib2
     */
    public String getStrPersLib2() {
        return strPersLib2;
    }

    /**
     * @param strPersLib2 the strPersLib2 to set
     */
    public void setStrPersLib2(String strPersLib2) {
        this.strPersLib2 = strPersLib2;
    }

    /**
     * @return the strPersURL2
     */
    public String getStrPersURL2() {
        return strPersURL2;
    }

    /**
     * @param strPersURL2 the strPersURL2 to set
     */
    public void setStrPersURL2(String strPersURL2) {
        this.strPersURL2 = strPersURL2;
    }

    /**
     * @return the bSuivantPrecedent
     */
    public boolean isbSuivantPrecedent() {
        return bSuivantPrecedent;
    }

    /**
     * @param bSuivantPrecedent the bSuivantPrecedent to set
     */
    public void setbSuivantPrecedent(boolean bSuivantPrecedent) {
        this.bSuivantPrecedent = bSuivantPrecedent;
    }

    /**
     * @return the imgBoutons
     */
    public Image[] getImgBoutons() {
        return imgBoutons;
    }

    /**
     * @param imgBoutons the imgBoutons to set
     */
    public void setImgBoutons(Image[] imgBoutons) {
        this.imgBoutons = imgBoutons;
    }

    /**
     * @return the strNomImagesBoutons
     */
    public String[] getStrNomImagesBoutons() {
        return strNomImagesBoutons;
    }

    /**
     * @param strNomImagesBoutons the strNomImagesBoutons to set
     */
    public void setStrNomImagesBoutons(String[] strNomImagesBoutons) {
        this.strNomImagesBoutons = strNomImagesBoutons;
    }

    /**
     * @return the prLisBoutons
     */
    public PixelReader[] getPrLisBoutons() {
        return prLisBoutons;
    }

    /**
     * @param prLisBoutons the prLisBoutons to set
     */
    public void setPrLisBoutons(PixelReader[] prLisBoutons) {
        this.prLisBoutons = prLisBoutons;
    }

    /**
     * @return the wiNouveauxBoutons
     */
    public WritableImage[] getWiNouveauxBoutons() {
        return wiNouveauxBoutons;
    }

    /**
     * @param wiNouveauxBoutons the wiNouveauxBoutons to set
     */
    public void setWiNouveauxBoutons(WritableImage[] wiNouveauxBoutons) {
        this.wiNouveauxBoutons = wiNouveauxBoutons;
    }

    /**
     * @return the pwNouveauxBoutons
     */
    public PixelWriter[] getPwNouveauxBoutons() {
        return pwNouveauxBoutons;
    }

    /**
     * @param pwNouveauxBoutons the pwNouveauxBoutons to set
     */
    public void setPwNouveauxBoutons(PixelWriter[] pwNouveauxBoutons) {
        this.pwNouveauxBoutons = pwNouveauxBoutons;
    }

    /**
     * @return the iNombreImagesBouton
     */
    public int getiNombreImagesBouton() {
        return iNombreImagesBouton;
    }

    /**
     * @param iNombreImagesBouton the iNombreImagesBouton to set
     */
    public void setiNombreImagesBouton(int iNombreImagesBouton) {
        this.iNombreImagesBouton = iNombreImagesBouton;
    }

    /**
     * @return the imgMasque
     */
    public Image getImgMasque() {
        return imgMasque;
    }

    /**
     * @param imgMasque the imgMasque to set
     */
    public void setImgMasque(Image imgMasque) {
        this.imgMasque = imgMasque;
    }

    /**
     * @return the prLisMasque
     */
    public PixelReader getPrLisMasque() {
        return prLisMasque;
    }

    /**
     * @param prLisMasque the prLisMasque to set
     */
    public void setPrLisMasque(PixelReader prLisMasque) {
        this.prLisMasque = prLisMasque;
    }

    /**
     * @return the wiNouveauxMasque
     */
    public WritableImage getWiNouveauxMasque() {
        return wiNouveauxMasque;
    }

    /**
     * @param wiNouveauxMasque the wiNouveauxMasque to set
     */
    public void setWiNouveauxMasque(WritableImage wiNouveauxMasque) {
        this.wiNouveauxMasque = wiNouveauxMasque;
    }

    /**
     * @return the pwNouveauxMasque
     */
    public PixelWriter getPwNouveauxMasque() {
        return pwNouveauxMasque;
    }

    /**
     * @param pwNouveauxMasque the pwNouveauxMasque to set
     */
    public void setPwNouveauxMasque(PixelWriter pwNouveauxMasque) {
        this.pwNouveauxMasque = pwNouveauxMasque;
    }

    /**
     * @return the imagesFond
     */
    public ImageFond[] getImagesFond() {
        return imagesFond;
    }

    /**
     * @param imagesFond the imagesFond to set
     */
    public void setImagesFond(ImageFond[] imagesFond) {
        this.imagesFond = imagesFond;
    }

    /**
     * @return the iNombreImagesFond
     */
    public int getiNombreImagesFond() {
        return iNombreImagesFond;
    }

    /**
     * @param iNombreImagesFond the iNombreImagesFond to set
     */
    public void setiNombreImagesFond(int iNombreImagesFond) {
        this.iNombreImagesFond = iNombreImagesFond;
    }

    /**
     * @return the strStyleHotSpots
     */
    public String getStrStyleHotSpots() {
        return strStyleHotSpots;
    }

    /**
     * @param strStyleHotSpots the strStyleHotSpots to set
     */
    public void setStrStyleHotSpots(String strStyleHotSpots) {
        this.strStyleHotSpots = strStyleHotSpots;
    }

    /**
     * @return the strStyleHotSpotImages
     */
    public String getStrStyleHotSpotImages() {
        return strStyleHotSpotImages;
    }

    /**
     * @param strStyleHotSpotImages the strStyleHotSpotImages to set
     */
    public void setStrStyleHotSpotImages(String strStyleHotSpotImages) {
        this.strStyleHotSpotImages = strStyleHotSpotImages;
    }

    /**
     * @return the zonesBarrePersonnalisee
     */
    public ZoneTelecommande[] getZonesBarrePersonnalisee() {
        return zonesBarrePersonnalisee;
    }

    /**
     * @param zonesBarrePersonnalisee the zonesBarrePersonnalisee to set
     */
    public void setZonesBarrePersonnalisee(ZoneTelecommande[] zonesBarrePersonnalisee) {
        this.zonesBarrePersonnalisee = zonesBarrePersonnalisee;
    }

}
