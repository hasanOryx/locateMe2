package oryx.kortex.locateme.objects

val urgent = """
    (عاجل)|(مهم)|(مستعجل)|(ضروري)
    |(?i)
    (Urgent)|(срочный)|(срочный)|(brådskande)
    |(urgjent)|(premiazko)|(тэрміновы)|(hitan)|(спешно)|(Naléhavé)|(presserende)
    |(dringend)|(kiireloomuline)|(kiireellinen)|(urxente)|(dringend)|(επείγων)
    |(sürgős)|(Brýn)|(práinneach)|(urgente)|(steidzams)|(skubus)
    |(итно)|(urġenti)|(som haster)|(pilny)|(urgente)|(Kje si)|(Var är du)
    (?-i)""".trimIndent().replace("\n","").toRegex(RegexOption.IGNORE_CASE)