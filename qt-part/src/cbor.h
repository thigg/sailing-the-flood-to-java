// This is free and unencumbered software released into the public domain.
#pragma once
#if __cplusplus < 201103
#warning "To enable all features you must compile with -std=c++11"
#endif
#include <iostream>
#include <map>
#include <stdint.h>
#include <vector>
#if __cplusplus >= 201103
#include <initializer_list>
#endif
class cbor {
public:
    enum type_t {
        TYPE_UNSIGNED,
        TYPE_NEGATIVE,
        TYPE_BINARY,
        TYPE_STRING,
        TYPE_ARRAY,
        TYPE_MAP,
        TYPE_TAGGED,
        TYPE_SIMPLE,
        TYPE_FLOAT
    };
    typedef std::vector <unsigned char> binary;
    typedef std::string string;
    typedef std::vector <cbor> array;
    typedef std::map <cbor, cbor> map;
    enum simple {
        SIMPLE_FALSE = 20,
        SIMPLE_TRUE,
        SIMPLE_NULL,
        SIMPLE_UNDEFINED,
        null = SIMPLE_NULL,
        undefined = SIMPLE_UNDEFINED
    };

    cbor (unsigned short value);
    cbor (unsigned value);
    cbor (unsigned long value);
    cbor (unsigned long long value);
    cbor (short value);
    cbor (int value);
    cbor (long value);
    cbor (long long value);
    cbor (const cbor::binary &value);
    cbor (const cbor::string &value);
    cbor (const char *value);
    cbor (const cbor::array &value);
    cbor (const cbor::map &value);
    static cbor tagged (unsigned long long tag, const cbor &value);
    cbor (cbor::simple value = cbor::SIMPLE_UNDEFINED);
    cbor (bool value);
    cbor (float value);
    cbor (double value);
#if __cplusplus >= 201103
    cbor (std::nullptr_t);
#endif

    bool is_unsigned () const;
    bool is_signed () const;
    bool is_int () const;
    bool is_binary () const;
    bool is_string () const;
    bool is_array () const;
    bool is_map () const;
    bool is_tagged () const;
    bool is_simple () const;
    bool is_bool () const;
    bool is_null () const;
    bool is_undefined () const;
    bool is_float () const;
    bool is_number () const;

    uint64_t to_unsigned () const;
    int64_t to_signed () const;
    cbor::binary to_binary () const;
    cbor::string to_string () const;
    cbor::array to_array () const;
    cbor::map to_map () const;
    cbor::simple to_simple () const;
    bool to_bool () const;
    double to_float () const;

    operator unsigned short () const;
    operator unsigned () const;
    operator unsigned long () const;
    operator unsigned long long () const;
    operator short () const;
    operator int () const;
    operator long () const;
    operator long long () const;
    operator cbor::binary () const;
    operator cbor::string () const;
    operator cbor::array () const;
    operator cbor::map () const;
    operator cbor::simple () const;
    operator bool () const;
    operator double () const;

    uint64_t tag () const;
    cbor child () const;

    cbor::type_t type () const;

    bool read (std::istream &in);
    void write (std::ostream &out) const;

    static bool validate (const cbor::binary &in);
    static cbor decode (const cbor::binary &in);
    static cbor::binary encode (const cbor &in);
    static cbor::string debug (const cbor &in);

    bool operator == (const cbor &other) const;
    bool operator != (const cbor &other) const;

    bool operator < (const cbor &other) const;
private:
    cbor::type_t m_type;
    union {
        uint64_t m_value;
        double m_float;
    };
    cbor::binary m_binary;
    cbor::string m_string;
    cbor::array m_array;
    cbor::map m_map;
};
