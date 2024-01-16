#ifndef CUSTOMPUSHBUTTON_H
#define CUSTOMPUSHBUTTON_H

#include <QPushButton>
#include <QEvent>
#include <QDebug>
#include <QPropertyAnimation>

class CustomPushButton : public QPushButton {
public:
    CustomPushButton(QWidget *parent = nullptr) : QPushButton(parent) {}

protected:
    void enterEvent(QEnterEvent *event) override {
        setCursor(Qt::PointingHandCursor);
        QPushButton::enterEvent(event);
    }

    void leaveEvent(QEvent *event) override {
        setCursor(Qt::ArrowCursor);
        QPushButton::leaveEvent(event);
    }
};


#endif // CUSTOMPUSHBUTTON_H
