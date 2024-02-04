using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem;
public class Player : MonoBehaviour
{
    private Vector2 rawInput;
    [SerializeField] private float moveSpeed = 50f;
    private Vector2 minBounds;
    private Vector2 maxBounds;
    [SerializeField] private float paddingLeft;
    [SerializeField] private float paddingRight;
    [SerializeField] private float paddingTop;
    [SerializeField] private float paddingBottom;
    private Shooter shooter;

    private void Awake()
    {
        shooter = GetComponent<Shooter>();
    }

    private void Start()
    {
        InitBounds();
    }

    void Update()
    {
        Move();
    }

    private void InitBounds()
    {
        Camera camera = Camera.main;
        minBounds = camera.ViewportToWorldPoint(new Vector2(0,0));
        maxBounds = camera.ViewportToWorldPoint(new Vector2(1,1));
    }
    private void Move()
    {
        Vector2 delta = rawInput * (moveSpeed * Time.deltaTime);
        Vector2 newPos = new Vector2();
        var position = transform.position;
        newPos.x = Mathf.Clamp(position.x + delta.x ,minBounds.x + paddingLeft, maxBounds.x - paddingRight);
        newPos.y = Mathf.Clamp(position.y + delta.y ,minBounds.y + paddingBottom, maxBounds.y - paddingTop);
        position = newPos;
        transform.position = position;
    }

    void OnMove(InputValue value)
    {
        rawInput = value.Get<Vector2>();
    }

    void OnFire(InputValue value)
    {
        if (shooter == null)
        {
            return;
        }

        shooter.isFiring = value.isPressed;
       
    }
}
