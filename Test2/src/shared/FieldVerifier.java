package shared;

public class FieldVerifier {

	public static boolean userNameVerifier(String username){
		if(username.length() > 6){ 
			System.out.println("true");
			return true;
		}else{
			System.out.println("false");

			return false;
		}
	}
	public static boolean emailVerifier(String email){
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\+@([\\w]+\\.)+[\\w]+[\\w]$";
		boolean isEmailVerified = false;
		int comma;
		int ad;
		String third = null;
		if(email.contains("@") && email.contains(".") && !email.contains(EMAIL_REGEX)){
			ad = email.indexOf('@')+1;		
			comma = email.lastIndexOf('.')+1;
			third = email.substring(comma);


			//Checker om emailen er sat ordenligt op
			//Der skal være 2 eller 3 tegn efter sidste comma og
			//der SKAL være ét tegn før og efter @

			if(third.length()==2 || third.length() == 3){
				if(ad != 0 & ad < comma-1){
					isEmailVerified = true;
				}else{
					isEmailVerified = false;
				}
			}else{
				isEmailVerified = false;
			}
		}
	
		return isEmailVerified;
	}

	public static boolean passwordVerifier(String pass){
		if(pass.length() < 6 || pass.length() > 12) return false;
		else return true;
	}
	public static boolean confirmedPasswordVerifier(String pass, String confirmedPassword){
		if(pass.equals(confirmedPassword)){
			return true;
		}else{
			return false;
		}
	}

}
