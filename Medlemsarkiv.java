import java.time.LocalDate;
import java.util.Random;
import java.time.Period;
import java.util.ArrayList;
/**
 * Personalia.java
 *
 * Klasse med personopplysninger: fornavn, etternavn, epostadresse og passord.
 * Passordet kan endres, men da må det nye være forskjellig fra det gamle.
 * Passordkontrollen skiller ikke mellom store og små bokstaver.
 */
class Personalia {
  private final String etternavn;
  private final String fornavn;
  private final String ePostadr;
  private String passord;

  /**
   * Konstruktør:
   * Alle data må oppgis: fornavn, etternavn, ePostadr, passord
   * Ingen av dataene kan være null eller blanke strenger.
   */
  public Personalia(String fornavn, String etternavn, String ePostadr, String passord) {
    if (fornavn == null || etternavn == null || ePostadr == null || passord == null ||
        fornavn.trim().equals("") || etternavn.trim().equals("") || ePostadr.trim().equals("") || passord.trim().equals("")) {
          throw new IllegalArgumentException("Et eller flere konstruktrørargumenter er null og/eller blanke.");
    }
    this.fornavn = fornavn.trim();
    this.etternavn = etternavn.trim();
    this.ePostadr = ePostadr.trim();
    this.passord = passord.trim();
  }

  public String getFornavn() {
    return fornavn;
  }

  public String getEtternavn() {
    return etternavn;
  }

  public String getEPostadr() {
    return ePostadr;
  }

  /**
   * Metoden returnerer true dersom passordet er korrekt.
   * Passordkontrollen skiller ikke mellom store og små bokstaver.
   */
  public boolean okPassord(String passordet) {
    return passord.equalsIgnoreCase(passordet);
  }

  /**
   * Metoden setter nytt passord, dersom det er forskjellig fra
   * det gamle. To passord betraktes som like dersom det kun er
   * forskjeller i store/små bokstaver.
   *
   * Metoden returnerer true dersom passordet ble endret, ellers false.
   */
  public boolean endrePassord(String gml, String nytt) {
    if (gml == null || nytt == null) {
      return false;
    }
    if (!passord.equalsIgnoreCase(gml.trim())) {
      return false;
    } else {
      passord = nytt.trim();
      return true;
    }
  }
}

public class BonusMedlem {
	private final int medlNr;
	private final Personalia pers;
	private final LocalDate innmeldtDato;
	private int poeng = 0;
	private final double FAKTOR;

	public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato) {
		this.medlNr = medlNr;
		this.pers = pers;
		this.innmeldtDato = innmeldtDato;
		this.FAKTOR = 1;
	}

	public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng, double FAKTOR) {
		this.medlNr = medlNr;
		this.pers = pers;
		this.innmeldtDato = innmeldtDato;
		this.poeng = poeng;
		this.FAKTOR = FAKTOR;
	}

	public int getMedlNr() {
		return medlNr;
	}

	public Personalia getPersonalia() {
		return pers;
	}

	public LocalDate getInnmeldt() {
		return innmeldtDato;
	}

	public int getPoeng() {
		return poeng;
	}

	public int finnKvalPoeng() {
		LocalDate nå = LocalDate.now();
		int dagerMellom = Period.between(getInnmeldt(), nå).getDays();
		if(dagerMellom > 365) {
			return 0;
		} else {
			return poeng;
		}
	}

	public boolean okPassord(String passord) {
		return pers.okPassord(passord);
	}

	public void registrerPoeng(int merPoeng) {
		if(merPoeng < 0) {
		throw new IllegalArgumentException("Poeng må være større enn null");
		} else {
			poeng += (merPoeng * FAKTOR);
		}
	}

	public String toString() {
	return "Navn: " + pers.getFornavn() + " " + pers.getEtternavn() +
		"\nEpost: " + pers.getEPostadr();
}

class GullMedlem extends BonusMedlem{

	public GullMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng) {
		super(medlNr, pers, innmeldtDato, poeng, 1.5);
	}


}

class SoelvMedlem extends BonusMedlem{

	public SoelvMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng) {
		super(medlNr, pers, innmeldtDato, poeng, 1.2);
	}
}

class BasicMedlem extends BonusMedlem{
	public BasicMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato) {
		super(medlNr, pers, innmeldtDato);
	}
}

class Medlemsarkiv {
	private ArrayList<BonusMedlem> liste = new ArrayList<BonusMedlem>();
	Random random = new Random();

	private int finnLedigNr() {
		int nr = 0;
		boolean gyldigNr = false;
		while(!gyldigNr) {
			nr = random.nextInt(Integer.MAX_VALUE);
			gyldigNr = true;

			//Sjekker om det nummeret som er satt allerede er opptatt
			for(BonusMedlem medlem : liste) {
				if(medlem.getMedlNr() == nr) {
					gyldigNr = false;
				}
			}
		}
		return nr;
	}

	public int nyMedlem(Personalia pers, LocalDate innmeldt) {
		int medlNr = finnLedigNr();
		BasicMedlem nyttMedlem = new BasicMedlem(medlNr, pers, innmeldt);
		liste.add(nyttMedlem);
		return medlNr;
	}

	public boolean registrerPoeng(int medlNr, int poeng) {
		if(poeng > 0) {
			for (BonusMedlem medlem : liste) {
				if(medlem.getMedlNr() == medlNr) {
					medlem.registrerPoeng(poeng);
					return true;
				}
			}
		}
		return false;
	}

	public int finnPoeng(int medlNr, String passord) {
		for(BonusMedlem medlem : liste) {
			if(medlem.getMedlNr() == medlNr && medlem.okPassord(passord)) {
				return medlem.getPoeng();
			}
		}
		return -1;
	}

	public void sjekkMedlemmer() {
		for(BonusMedlem medlem : liste) {
			if(medlem instanceof BasicMedlem) {
				//Sjekker om medlemmet er BasicMedlem fra før
				if(medlem.finnKvalPoeng() >= 25000){
					//Sjekker om medlemmet er kvalifisert for å bli SoelvMedlem
					if(medlem.finnKvalPoeng() >= 75000) {
						//Sjekker om medlemmet er kvalifistert for å bli GullMedlem
						GullMedlem gull = new GullMedlem(medlem.getMedlNr(), medlem.getPersonalia(), medlem.getInnmeldt(), medlem.getPoeng());
						liste.set(liste.indexOf(medlem), gull);
					} else {
						//Hvis ikke man oppfyller kravene til gull, blir man SoelvMedlem
						SoelvMedlem soelv = new SoelvMedlem(medlem.getMedlNr(), medlem.getPersonalia(), medlem.getInnmeldt(), medlem.getPoeng());
						liste.set(liste.indexOf(medlem), soelv);

					}
				}
			}else if (medlem instanceof SoelvMedlem) {
				if(medlem.finnKvalPoeng() >= 75000) {
					//Hvis medlemmet er kvalifisert for å bli GullMedlem
					GullMedlem gull = new GullMedlem(medlem.getMedlNr(), medlem.getPersonalia(), medlem.getInnmeldt(), medlem.getPoeng());
					liste.set(liste.indexOf(medlem), gull);
				}
			}
		}
	}

	public BonusMedlem getBonusMedlem(int medlNr) {
		for(BonusMedlem medlem : liste) {
			if(medlem.getMedlNr() == medlNr) {
				return medlem;
			}
		}
		return null;
	}
}

public static void main(String[] args){
	Medlemsarkiv arkiv = new Medlemsarkiv();

	Personalia per = new Personalia("Hansen", "Per", "per_hansen@online.no", "per123");
	Personalia ola = new Personalia("Nordmann", "Ola", "ola_nord@online.no", "ola321");

	int medl1 = arkiv.nyMedlem(per, LocalDate.now().minusDays(5)); //Kan oppgradere
	int medl2 = arkiv.nyMedlem(ola, Localdate.now().minusYears(2)); //Kan ikke oppgrader

	//Begge medlemmer har nok poeng til å oppdatere
	arkiv.registrerPoeng(medl1, 75000);
	arkiv.registrerPoeng(medl2, 30000);

	arkiv.sjekkMedlemmer();

	//Test 1
	if(arkiv.getBonusMedlem(medl1) instanceof GullMedlem){
		System.out.println("Test 1 er vellykket");
	}else{
		System.out.println("Test 1 er ikke vellykket");
	}

	//Test 2
	if(arkiv.getBonusMedlem(medl2) instanceof BasicMedlem) {
		System.out.println("Test 1 er vellykket");
	}else {
		System.out.println("Test 1 er ikke vellykket");
	}

}
}