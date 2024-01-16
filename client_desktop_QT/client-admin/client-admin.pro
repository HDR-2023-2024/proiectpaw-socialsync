QT       += core gui network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++17

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    loginform.cpp \
    main.cpp \
    mainwindow.cpp \
    resourcedetails.cpp \
    resourcepanel.cpp

HEADERS += \
    custompushbutton.h \
    loginform.h \
    mainwindow.h \
    resourcedetails.h \
    resourcepanel.h

FORMS += \
    loginform.ui \
    mainwindow.ui \
    resourcedetails.ui \
    resourcepanel.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

DISTFILES += \
    styles.css

OTHER_FILES +=

RESOURCES += \
    resources.qrc
