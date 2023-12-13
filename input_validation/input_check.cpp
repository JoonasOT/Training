#include "input_check.h"
#include <sstream>

/* Strip string from the left */
inline std::string& ltrim(std::string& s, const char* t = " \t\n\r\f\v") {
	s.erase(0, s.find_first_not_of(t));
	return s;
}

bool Input::add_function(const std::string& s, const func& f) {
	if ( m_functions.count(s) ) return false;

	m_functions[s] = f;
	return true;
}

bool Input::delete_function(const std::string& s) {
	if ( !m_functions.count(s) ) return false;

	m_functions.erase(s);
	return true;
}


bool Input::check_valid(const std::string& f, const std::string& in) const {
	if ( !m_functions.count(f) ) return false;

	
	std::vector<std::string> args;
	{
		std::string tmp;
		std::stringstream ss(in);
		while (std::getline(ss, tmp, ',')) args.push_back(ltrim(tmp));
	}
	if ( m_functions.at(f).arguments.size() != args.size()) return false;


	for (unsigned int i = 0; i < args.size(); i++ ) {
		bool ok = false;

		switch ( m_functions.at(f).arguments.at(i) )
		{
		case INT:
			ok = is_int(args.at(i));
			break;
		case FLOAT:
			ok = is_float(args.at(i));
			break;
		case STRING:
			ok = is_string(args.at(i));
			break;
		default: /* WTF? */
			break;
		}
		if (!ok) return false;
	}
	return true;
}

std::optional<parse_result> Input::parse(const std::string& f, const std::string& in) const {
	
	if ( !check_valid(f, in) ) return {};

	std::vector<std::string> args;
	{
		std::string tmp;
		std::stringstream ss(in);
		while (std::getline(ss, tmp, ',')) args.push_back(ltrim(tmp));
	}

	parse_result res;
	res.reserve(args.size());

	for (unsigned int i = 0; i < args.size(); i++) {

		switch (m_functions.at(f).arguments.at(i))
		{
		case INT:
			res.push_back({ INT, atoi(args.at(i).c_str()) });
			break;
		case FLOAT:
			res.push_back({ FLOAT, std::stof(args.at(i)) });
			break;
		case STRING:
			res.push_back({ STRING, args.at(i) });
			break;
		}
	}

	return res;
}

bool Input::is_int(const std::string& s) const {
	for (const auto& c : s) if (!std::isdigit(c)) return false;
	return true;
}

bool Input::is_float(const std::string& s) const {
	std::istringstream ss(s);
	float f;
	ss >> std::noskipws >> f;
	return ss.eof() && !ss.fail();
}

bool Input::is_string(const std::string& s) const {
	for (const char& c : s) if (!(std::isalnum(c)) && !special_char_is_allowed(c)) return false;
	return true;
}

bool Input::special_char_is_allowed(const char& c) const {
	for (unsigned int i = 0; m_chars_allowed[i] != '\0'; i++) if (m_chars_allowed[i] == c) return true;
	return false;
}